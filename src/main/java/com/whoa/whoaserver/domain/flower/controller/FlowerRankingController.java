package com.whoa.whoaserver.domain.flower.controller;

import com.whoa.whoaserver.domain.flower.service.FlowerRankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Flower Ranking", description = "추가 정보 필요 X")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FlowerRankingController {

    private final FlowerRankingService flowerRankingService;

    @GetMapping("ranking")
    @Operation(summary = "저렴한 꽃 순위 조희", description = "가장 저렴한 꽃 5개를 조회합니다.")
    public ResponseEntity<?> getFlowerRanking(){
        return ResponseEntity.ok().body(flowerRankingService.getFlowerRanking());
    }

}
