package com.whoa.whoaserver.bouquet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.bouquet.dto.response.BouquetCustomizingResponseV2;
import com.whoa.whoaserver.bouquet.dto.response.BouquetOrderResponse;
import com.whoa.whoaserver.bouquet.service.BouquetCustomizingServiceV2;
import com.whoa.whoaserver.global.annotation.DeviceUser;
import com.whoa.whoaserver.global.dto.UserContext;
import com.whoa.whoaserver.global.exception.WhoaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.whoa.whoaserver.global.exception.ExceptionCode.IMAGE_UPLOAD_ERROR;
import static com.whoa.whoaserver.global.exception.ExceptionCode.INVALID_BOUQUET_REQUEST_JSON_FORMAT;

@Tag(name = "Bouquet Customizing", description = "Header에 MEMBER_ID(key), 디바이스 등록 이후 반환 받은 id(value)로 요청해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bouquet")
public class BouquetCustomizingControllerV2 {

	private final BouquetCustomizingServiceV2 bouquetCustomizingService;
	private final ObjectMapper objectMapper;

	@PostMapping(value = "/customizing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "꽃다발 제작", description = "꽃다발 주문과 이미지를 등록합니다.")
	public ResponseEntity<BouquetCustomizingResponseV2> registerBouquet(
		@DeviceUser UserContext userContext,
		@Valid @RequestPart("request") String bouquetRequest,
		@RequestPart("imgUrl") List<MultipartFile> multipartFiles
	) {

		try {
			Long memberId = userContext.id();
			BouquetCustomizingRequest request = objectMapper.readValue(bouquetRequest, BouquetCustomizingRequest.class);
			BouquetCustomizingResponseV2 response = bouquetCustomizingService.registerBouquet(request, memberId, multipartFiles);
			return ResponseEntity.ok(response);
		} catch (JsonProcessingException e) {
			throw new WhoaException(INVALID_BOUQUET_REQUEST_JSON_FORMAT);
		} catch (Exception e) {
			throw new WhoaException(IMAGE_UPLOAD_ERROR);
		}

	}

	@PutMapping("/customizing/{bouquetId}")
	@Operation(summary = "꽃다발 수정", description = "유저의 꽃다발 주문서와 이미지를 수정합니다.")
	public ResponseEntity<BouquetCustomizingResponseV2> updateBouquet(
		@DeviceUser UserContext userContext,
		@Valid @RequestPart("request") String bouquetRequest,
		@RequestPart("imgUrl") List<MultipartFile> multipartFiles,
		@PathVariable("bouquetId") final Long bouquetId
	) {

		try {
			Long memberId = userContext.id();
			BouquetCustomizingRequest request = objectMapper.readValue(bouquetRequest, BouquetCustomizingRequest.class);
			BouquetCustomizingResponseV2 response = bouquetCustomizingService.updateBouquet(request, memberId, bouquetId, multipartFiles);
			return ResponseEntity.ok(response);
		} catch (JsonProcessingException e) {
			throw new WhoaException(INVALID_BOUQUET_REQUEST_JSON_FORMAT);
		} catch (Exception e) {
			throw new WhoaException(IMAGE_UPLOAD_ERROR);
		}
	}

	@PatchMapping("/status/{bouquetId}")
	@Operation(summary = "꽃다발 실제 제작 완료 처리", description = "꽃다발 상세 조회 페이지에서 제작 완료 버튼을 누르면 전체 조회 시 제작 완료 항목으로 반환됩니다.")
	public void updateBouquetStatus(
		@DeviceUser UserContext userContext,
		@PathVariable("bouquetId") final Long bouquetId
	) {
		Long memberId = userContext.id();
		bouquetCustomizingService.updateBouquetStatus(memberId, bouquetId);
	}

	@GetMapping("/status")
	@Operation(summary = "꽃다발 제작 완료 여부에 따른 전체 조회", description = "마이 페이지에서 저장된 요구서와 제작 완료 항목을 분리하여 반환합니다.")
	public ResponseEntity<Map<String, List<BouquetOrderResponse>>> getAllBouquetsByBouquetStatus(
		@DeviceUser UserContext userContext
	) {
		Long memberId = userContext.id();
		Map<String, List<BouquetOrderResponse>> response = bouquetCustomizingService.getAllBouquetsByBouquetStatus(memberId);
		return ResponseEntity.ok(response);
	}

}