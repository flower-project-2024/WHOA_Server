package com.whoa.whoaserver.bouquet.controller;

import java.net.URL;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoa.whoaserver.bouquet.dto.request.PresignedUrlRequest;
import com.whoa.whoaserver.bouquet.dto.response.PresignedUrlResponse;
import com.whoa.whoaserver.bouquet.service.BouquetImageService;
import com.whoa.whoaserver.global.annotation.DeviceUser;
import com.whoa.whoaserver.global.dto.UserContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Bouquet Image", description = "Bouquet Image API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class BouquetImageController {
    
    private final BouquetImageService bouquetImageService;
    
    @PostMapping("/presigned_url")
    @Operation(summary = "이미지 등록 주소", description = "S3 이미지 저장을 위한 주소를 반환합니다.")
    public ResponseEntity<PresignedUrlResponse> providePresignedUrl(@DeviceUser UserContext userContext, @RequestBody PresignedUrlRequest request) {
        
        URL presignedUrl = bouquetImageService.createPresignedUrl(userContext, request);

        return ResponseEntity.ok(PresignedUrlResponse.create(presignedUrl));
    }
}
