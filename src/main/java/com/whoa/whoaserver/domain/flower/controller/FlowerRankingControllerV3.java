package com.whoa.whoaserver.domain.flower.controller;

import com.whoa.whoaserver.domain.flower.dto.response.FlowerPopularityResponseDto;
import com.whoa.whoaserver.domain.flower.service.FlowerRankingServiceV3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "3차 MVP Flower Ranking 중 추가된 API 목록", description = "header에 MEMBER_ID 필요 없습니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3")
public class FlowerRankingControllerV3 {

	private final FlowerRankingServiceV3 flowerRankingServiceV3;

	@GetMapping("/flower/popularity/ranking")
	@Operation(summary = "인기있는 꽃 순위 정보 조회", description = "꽃다발 구성에 가장 많이 이용된 꽃 랭킹 1위부터 5위까지 반환합니다.")
	public List<FlowerPopularityResponseDto> getFlowerPopularityRanking() {
		return flowerRankingServiceV3.getFlowerPopularityRanking();
	}
}
