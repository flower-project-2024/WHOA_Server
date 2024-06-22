package com.whoa.whoaserver.flower.controller;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.dto.FlowerRequestDto;
import com.whoa.whoaserver.flower.service.FlowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Flower Detail&Recommend", description = "PathVariable 필요")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flower")
public class FlowerController {

    @Autowired
    private final FlowerService flowerService;

    @PostMapping()
    @Operation(summary = "꽃 등록", description = "데이터 저장용 API입니다.")
    public ResponseEntity<?> postFlower(Flower flower) {
        return ResponseEntity.ok().body(flowerService.postFlower(flower));
    }

    @GetMapping("detail/{flowerId}")
    @Operation(summary = "꽃 상세 조회", description = "꽃을 상세 조회 합니다.")
    public ResponseEntity<?> getFlower(@PathVariable("flowerId") final Long flowerId){
        return ResponseEntity.ok().body(flowerService.getFlower(flowerId));
    }

    @GetMapping("recommend/{month}/{date}")
    @Operation(summary = "오늘의 꽃 추천", description = "오늘의 꽃을 추천합니다.")
    public ResponseEntity<?> getRecommendFlower(@PathVariable("month") final int month, @PathVariable("date") final int date){
        return ResponseEntity.ok().body(flowerService.getRecommendFlower(month, date));
    }

    @GetMapping("/search")
    @Operation(summary = "꽃 검색", description = "꽃 검색을 위해 모든 꽃 종류를 반환합니다.")
    public ResponseEntity<?> getAllFlowers() {
        return ResponseEntity.ok().body(flowerService.getAllFlowers());
    }
}
