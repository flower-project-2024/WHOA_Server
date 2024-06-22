package com.whoa.whoaserver.flowerExpression.controller;

import com.whoa.whoaserver.flower.service.FlowerService;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.service.FlowerExpressionService;
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
@RequestMapping("/api/flowerExpressions")
public class FlowerExpressionController {
    @Autowired
    private final FlowerExpressionService flowerExpressionService;

    @PostMapping("{flowerId}")
    @Operation(summary = "꽃말과 사진 등록", description = "데이터 저장용 API입니다.")
    public ResponseEntity<?> postFlower(@RequestParam("Image") MultipartFile Image, @PathVariable Long flowerId, FlowerExpression flowerExpression) throws IOException {
        return ResponseEntity.ok().body(flowerExpressionService.postFlowerExpressions(Image, flowerId, flowerExpression));
    }

}
