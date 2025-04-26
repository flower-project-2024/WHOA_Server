package com.whoa.whoaserver.global.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AccessLogFilter implements Filter {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req);
		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

		chain.doFilter(requestWrapper, responseWrapper);

		String logEntry = buildLogEntry(requestWrapper, responseWrapper, req, (HttpServletResponse) response);
		writeLog(logEntry);

		responseWrapper.copyBodyToResponse();
	}


	private String buildLogEntry(ContentCachingRequestWrapper requestWrapper, ResponseWrapper responseWrapper,
								 HttpServletRequest req, HttpServletResponse res) {
		String timestamp = LocalDateTime.now().format(DATE_FORMAT);
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.isEmpty()) {
			ip = req.getRemoteAddr();
		}

		String method = req.getMethod();
		String uri = req.getRequestURI();
		String queryString = req.getQueryString();

		String requestBody = getRequestBody(requestWrapper);
		String responseBody = responseWrapper.getCaptureAsString();

		queryString = (queryString != null) ? "?" + queryString : "";
		requestBody = (!requestBody.isEmpty()) ? requestBody : "-";
		responseBody = (!responseBody.isEmpty()) ? responseBody : "-";

		return String.format(
			"%s - [%s] %s - %s %s %d%n REQUEST_BODY=\"%s\" %n RESPONSE_BODY=\"%s\"%n",
			ip, timestamp, method, uri, queryString, res.getStatus(), requestBody, responseBody
		);
	}

	private void writeLog(String logEntry) {
		String logDir = "logs";
		String logFileName = "access_log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log";
		Path logFilePath = Paths.get(logDir, logFileName);

		try {
			Files.createDirectories(Paths.get(logDir));

			try (BufferedWriter writer = Files.newBufferedWriter(logFilePath,
				StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
				writer.write(logEntry);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("로그 파일 생성 실패: " + logFilePath);
			e.printStackTrace(System.err);
		}
	}

	private String getRequestBody(ContentCachingRequestWrapper request) {
		byte[] content = request.getContentAsByteArray();
		return (content.length > 0) ? new String(content, StandardCharsets.UTF_8) : "-";
	}

	private static class ResponseWrapper extends HttpServletResponseWrapper {
		private final ByteArrayOutputStream capture;
		private final ServletOutputStream output;

		public ResponseWrapper(HttpServletResponse response) throws IOException {
			super(response);
			capture = new ByteArrayOutputStream();
			output = new CustomServletOutputStream(capture);
		}

		@Override
		public ServletOutputStream getOutputStream() {
			return output;
		}

		@Override
		public void flushBuffer() throws IOException {
			output.flush();
			super.flushBuffer();
		}

		public String getCaptureAsString() {
			return capture.size() > 0 ? capture.toString(StandardCharsets.UTF_8) : "-";
		}

		public void copyBodyToResponse() throws IOException {
			HttpServletResponse response = (HttpServletResponse) getResponse();
			response.getOutputStream().write(capture.toByteArray());
			response.getOutputStream().flush();
		}
	}

	private static class CustomServletOutputStream extends ServletOutputStream {
		private final ByteArrayOutputStream outputStream;

		public CustomServletOutputStream(ByteArrayOutputStream outputStream) {
			this.outputStream = outputStream;
		}

		@Override
		public void write(int b) throws IOException {
			outputStream.write(b);
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			// 비동기 X
		}
	}
}
