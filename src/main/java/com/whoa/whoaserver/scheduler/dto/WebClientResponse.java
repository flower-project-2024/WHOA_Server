package com.whoa.whoaserver.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebClientResponse {

	@JsonProperty("response")
	private Response response;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Response {
		@JsonProperty("resultMsg")
		private String resultMsg;

		@JsonProperty("numOfRows")
		private int numOfRows;

		@JsonProperty("items")
		private Item[] items;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Item {
		@JsonProperty("pumName")
		private String pumName;

		@JsonProperty("avgAmt")
		private String avgAmt; // instanceof String
	}
}
