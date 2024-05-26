package com.whoa.whoaserver.bouquet.controller;

import java.net.URL;
import java.util.List;

import com.whoa.whoaserver.bouquet.dto.request.MultipartFileUploadRequest;
import com.whoa.whoaserver.global.config.S3Config;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.whoa.whoaserver.bouquet.dto.request.PresignedUrlRequest;
import com.whoa.whoaserver.bouquet.dto.response.PresignedUrlResponse;
import com.whoa.whoaserver.bouquet.service.BouquetImageService;
import com.whoa.whoaserver.global.annotation.DeviceUser;
import com.whoa.whoaserver.global.dto.UserContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Bouquet Image", description = "Header에 MEMBER_ID(key), 디바이스 등록 이후 반환 받은 id(value)로 요청해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class BouquetImageController {
    
    private final BouquetImageService bouquetImageService;
    private final S3Config s3Config;

    @PostMapping("/presigned_url")
    @Operation(summary = "단일 이미지 등록을 위한 presignedUrl 주소 반환", description = "한 번에 하나의 이미지만 업로드할 수 있습니다.")
    public ResponseEntity<PresignedUrlResponse> providePresignedUrl(@DeviceUser UserContext userContext, @RequestBody PresignedUrlRequest request) {
        
        URL presignedUrl = bouquetImageService.createPresignedUrl(userContext, request);

        return ResponseEntity.ok(PresignedUrlResponse.create(presignedUrl));
    }

    @PostMapping("/multipart-files")
    @Operation(summary = "다중 이미지 업로드", description = "한 번에 여러 개의 이미지를 업로드할 수 있습니다.")
    public ResponseEntity<Void> uploadMultipleFiles(@DeviceUser UserContext userContext,
                                                    @RequestPart("imgUrl") List<MultipartFile> multipartFiles,
                                                    @Valid @RequestBody MultipartFileUploadRequest request) {

        if (multipartFiles == null) {
            throw new WhoaException(ExceptionCode.NULL_INPUT_CONTENT);
        }

        List<String> imgPaths = s3Config.upload(multipartFiles);
        bouquetImageService.uploadMultipleFiles(userContext, imgPaths, request);

        return ResponseEntity.noContent().build();
    }

}
