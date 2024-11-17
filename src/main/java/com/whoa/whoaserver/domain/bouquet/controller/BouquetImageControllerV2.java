package com.whoa.whoaserver.domain.bouquet.controller;

import com.whoa.whoaserver.domain.bouquet.dto.response.RealBouquetImageResponse;
import com.whoa.whoaserver.domain.bouquet.service.BouquetImageServiceV2;
import com.whoa.whoaserver.global.annotation.DeviceUser;
import com.whoa.whoaserver.global.dto.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "2차 MVP Bouquet Image", description = "Header에 MEMBER_ID(key), 디바이스 등록 이후 반환 받은 id(value)로 요청해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/images")
public class BouquetImageControllerV2 {

	private final BouquetImageServiceV2 bouquetImageServiceV2;

	@PostMapping(value = "/multipart-file/{bouquetId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "실제 꽃다발 이미지 업로드", description = "실제 제작한 꽃다발 사진을 한 장 업로드합니다.")
	public ResponseEntity<RealBouquetImageResponse> uploadRealBouquetMultipleFile(
		@DeviceUser UserContext userContext,
		@PathVariable("bouquetId") final Long bouquetId,
		@RequestPart("imgFile") MultipartFile multipartFiles
	) {

		Long memberId = userContext.id();
		return ResponseEntity.ok().body(bouquetImageServiceV2.uploadRealBouquetMultipleFile(memberId, bouquetId, multipartFiles));
	}
}
