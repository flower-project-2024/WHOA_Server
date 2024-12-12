package com.whoa.whoaserver.domain.keyword.controller;

import com.whoa.whoaserver.domain.keyword.dto.response.FlowerInfoByKeywordResponseV2;
import com.whoa.whoaserver.domain.keyword.service.FlowerKeywordServiceV2;
import com.whoa.whoaserver.domain.keyword.service.FlowerKeywordServiceV3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "3차 MVP Flower Keyword", description = "Flower Keyword API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/flower/keyword")
public class FlowerKeywordControllerV3 {

	private final FlowerKeywordServiceV3 flowerKeywordServiceV3;

	@GetMapping
	@Operation(summary = "키워드별 꽃 조회", description = "키워드별 꽃 조회 시 구매 목적과 선택한 색상 계열 고려하여 그에 맞는 꽃을 추천합니다.")
	public List<FlowerInfoByKeywordResponseV2> getFlowerInfoByKeywordAndCustomizingPurposeAndColor(
		@RequestParam(value = "customizingPurposeId", required = false) Long customizingPurposeId,
		@RequestParam(value = "keywordId", required = false) Long keywordId,
		@RequestParam(value = "selectedColors", required = false) List<String> selectedColors
	) {
		return flowerKeywordServiceV3.getFlowerInfoByKeywordAndCustomizingPurposeAndColor(customizingPurposeId, keywordId, selectedColors);
	}
}
