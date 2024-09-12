package com.whoa.whoaserver.keyword.controller;

import com.whoa.whoaserver.keyword.service.FlowerKeywordServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2차 MVP Flower Keyword", description = "Flower Keyword API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/flower")
public class FlowerKeywordControllerV2 {

	private final FlowerKeywordServiceV2 flowerKeywordServiceV2;

	@GetMapping("/keyword")
	@Operation(summary = "키워드별 꽃 조회", description = "키워드별 꽃 조회 시 구매 목적을 고려하여 커스터마이징 할 수 있도록 꽃 정보를 제공합니다.")
	public ResponseEntity<?> getFlowerInfoByKeywordAndCustomizingPurpose(
		@RequestParam(value = "customizingPurposeId", required = false) Long customizingPurposeId,
		@RequestParam(value = "keywordId", required = false) Long keywordId
	) {
		return ResponseEntity.ok().body(flowerKeywordServiceV2.getFlowerInfoByKeywordAndCustomizingPurpose(customizingPurposeId, keywordId));
	}
}
