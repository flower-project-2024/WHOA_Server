package com.whoa.whoaserver.bouquet.controller;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoa.whoaserver.bouquet.dto.request.MultipartFileUploadRequest;
import com.whoa.whoaserver.bouquet.dto.response.MultipartFileUploadedUrlResponse;
import com.whoa.whoaserver.global.config.S3Config;
import com.whoa.whoaserver.global.exception.WhoaException;
import org.springframework.http.MediaType;
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

import static com.whoa.whoaserver.global.exception.ExceptionCode.*;

@Tag(name = "Bouquet Image", description = "Header에 MEMBER_ID(key), 디바이스 등록 이후 반환 받은 id(value)로 요청해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class BouquetImageController {

    private final BouquetImageService bouquetImageService;
    private final S3Config s3Config;
    private final ObjectMapper objectMapper;

    @PostMapping("/presigned_url")
    @Operation(summary = "단일 이미지 등록을 위한 presignedUrl 주소 반환", description = "한 번에 하나의 이미지만 업로드할 수 있습니다.")
    public ResponseEntity<PresignedUrlResponse> providePresignedUrl(@DeviceUser UserContext userContext, @RequestBody PresignedUrlRequest request) {

        URL presignedUrl = bouquetImageService.createPresignedUrl(userContext, request);

        return ResponseEntity.ok(PresignedUrlResponse.create(presignedUrl));
    }

    @PostMapping(value = "/multipart-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "다중 이미지 업로드", description = "한 번에 여러 개의 이미지를 업로드할 수 있습니다.")
    public ResponseEntity<MultipartFileUploadedUrlResponse> uploadMultipleFiles(@DeviceUser UserContext userContext,
                                                                                @RequestPart("imgUrl") List<MultipartFile> multipartFiles,
                                                                                @RequestPart("bouquetId") String bouquetIdJson) {
        try {
            MultipartFileUploadRequest request = objectMapper.readValue(bouquetIdJson, MultipartFileUploadRequest.class);
            List<String> imgPaths = s3Config.upload(multipartFiles);
            return ResponseEntity.ok(bouquetImageService.saveMultipleFilesUrl(userContext, imgPaths, request));
        } catch (JsonProcessingException e) {
            throw new WhoaException(INVALID_BOUQUET_ID_JSON_FORMAT);
        } catch (Exception e) {
            throw new WhoaException(IMAGE_UPLOAD_ERROR);
        }
    }

    @PatchMapping("/multipart-files/{bouquetImageId}")
    @Operation(summary = "이미지 수정", description = "기존 이미지를 새 이미지로 교체합니다.")
    public ResponseEntity<MultipartFileUploadedUrlResponse> updateFile(
            @DeviceUser UserContext userContext,
            @PathVariable Long bouquetImageId,
            @RequestPart("imgUrl") MultipartFile multipartFile) {
        try {
            String imgPath = s3Config.uploadSingleFile(multipartFile);
            return ResponseEntity.ok(bouquetImageService.updateMultipleFilesUrl(userContext, bouquetImageId, imgPath));
        } catch (Exception e) {
            throw new WhoaException(IMAGE_UPLOAD_ERROR);
        }
    }

    @DeleteMapping("/multipart-files/{bouquetImageId}")
    @Operation(summary = "이미지 삭제", description = "기존 이미지를 삭제합니다.")
    public ResponseEntity<Void> deleteFile(
            @DeviceUser UserContext userContext,
            @PathVariable Long bouquetImageId) {
        bouquetImageService.deleteMultipleFilesUrl(userContext, bouquetImageId);
        return ResponseEntity.noContent().build();
    }


}
