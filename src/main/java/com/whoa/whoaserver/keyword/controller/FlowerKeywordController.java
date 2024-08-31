package com.whoa.whoaserver.keyword.controller;

import com.whoa.whoaserver.keyword.service.FlowerKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1차 MVP Flower Keyword", description = "Flower Keyword API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flower")
public class FlowerKeywordController {

    private final FlowerKeywordService flowerKeywordService;

    @GetMapping("/keyword/{keyowrdId}")
    @Operation(summary = "키워드별 꽃 조회", description = "키워드별 꽃 조회를 위한 API입니다.")
    public ResponseEntity<?> getFlowerInfoByKeyword(@PathVariable("keyowrdId") final Long keywordId) {
        return ResponseEntity.ok().body(flowerKeywordService.getFlowerInfoByKeyword(keywordId));
    }
}
