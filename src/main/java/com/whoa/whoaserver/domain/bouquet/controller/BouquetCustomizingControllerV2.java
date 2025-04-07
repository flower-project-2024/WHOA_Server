package com.whoa.whoaserver.domain.bouquet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoa.whoaserver.domain.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.domain.bouquet.dto.request.BouquetNameUpdateRequest;
import com.whoa.whoaserver.domain.bouquet.dto.response.BouquetCustomizingResponseV2;
import com.whoa.whoaserver.domain.bouquet.dto.response.BouquetInfoDetailResponseV2;
import com.whoa.whoaserver.domain.bouquet.dto.response.BouquetOrderResponseV2;
import com.whoa.whoaserver.domain.bouquet.service.BouquetCustomizingServiceV2;
import com.whoa.whoaserver.global.annotation.DeviceUser;
import com.whoa.whoaserver.global.dto.UserContext;
import com.whoa.whoaserver.global.exception.WhoaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static com.whoa.whoaserver.global.exception.ExceptionCode.INVALID_BOUQUET_REQUEST_JSON_FORMAT;

@Tag(name = "2차 MVP Bouquet Customizing", description = "Header에 MEMBER_ID(key), 디바이스 등록 이후 반환 받은 id(value)로 요청해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bouquet")
public class BouquetCustomizingControllerV2 {

	private final BouquetCustomizingServiceV2 bouquetCustomizingServiceV2;
	private final ObjectMapper objectMapper;

	@PostMapping(value = "/customizing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "꽃다발 제작", description = "꽃다발 주문과 이미지를 등록합니다.")
	public ResponseEntity<BouquetCustomizingResponseV2> registerBouquet(
		@DeviceUser UserContext userContext,
		@Valid @RequestPart("request") String bouquetRequest,
		@RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles
	) {

		try {
			Long memberId = userContext.id();
			BouquetCustomizingRequest request = objectMapper.readValue(bouquetRequest, BouquetCustomizingRequest.class);

			if (multipartFiles == null) {
				multipartFiles = Collections.emptyList();
			}

			BouquetCustomizingResponseV2 response = bouquetCustomizingServiceV2.registerBouquet(request, memberId, multipartFiles);
			return ResponseEntity.ok(response);
		} catch (JsonProcessingException e) {
			throw new WhoaException(INVALID_BOUQUET_REQUEST_JSON_FORMAT);
		}
	}

	@PutMapping("/customizing/{bouquetId}")
	@Operation(summary = "꽃다발 수정", description = "유저의 꽃다발 주문서와 이미지를 수정합니다.")
	public ResponseEntity<BouquetCustomizingResponseV2> updateBouquet(
		@DeviceUser UserContext userContext,
		@Valid @RequestPart("request") String bouquetRequest,
		@RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles,
		@PathVariable("bouquetId") final Long bouquetId
	) {

		try {
			Long memberId = userContext.id();
			BouquetCustomizingRequest request = objectMapper.readValue(bouquetRequest, BouquetCustomizingRequest.class);

			if (multipartFiles == null) {
				multipartFiles = Collections.emptyList();
			}

			BouquetCustomizingResponseV2 response = bouquetCustomizingServiceV2.updateBouquet(request, memberId, bouquetId, multipartFiles);
			return ResponseEntity.ok(response);
		} catch (JsonProcessingException e) {
			throw new WhoaException(INVALID_BOUQUET_REQUEST_JSON_FORMAT);
		}
	}

	@PatchMapping("/status/{bouquetId}")
	@Operation(summary = "꽃다발 실제 제작 완료 처리", description = "꽃다발 상세 조회 페이지에서 제작 완료 버튼을 누르면 전체 조회 시 제작 완료 항목으로 반환됩니다.")
	public ResponseEntity<Void> updateBouquetStatus(
		@DeviceUser UserContext userContext,
		@PathVariable("bouquetId") final Long bouquetId
	) {
		Long memberId = userContext.id();
		bouquetCustomizingServiceV2.updateBouquetStatus(memberId, bouquetId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/all/status")
	@Operation(summary = "꽃다발 제작 완료 여부에 따른 전체 조회", description = "마이 페이지에서 상태를 추가하여 반환합니다.")
	public ResponseEntity<List<BouquetOrderResponseV2>> getAllBouquetsWithStatus(
		@DeviceUser UserContext userContext
	) {
		Long memberId = userContext.id();
		return ResponseEntity.ok().body(bouquetCustomizingServiceV2.getAllBouquetsWithStatus(memberId));
	}

	@GetMapping("/{bouquetId}")
	@Operation(summary = "꽃다발 주문서 단건 조회", description = "유저가 등록한 주문서 한 건을 상세 조회합니다.")
	public ResponseEntity<BouquetInfoDetailResponseV2> getBouquetDetails(
		@DeviceUser UserContext userContext,
		@PathVariable("bouquetId") final Long bouquetId
	) {
		BouquetInfoDetailResponseV2 response = bouquetCustomizingServiceV2.getBouquetDetails(userContext.id(), bouquetId);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{bouquetId}")
	@Operation(summary = "꽃다발 주문서 제목 변경", description = "꽃다발 주문서의 제목만 변경합니다.")
	public ResponseEntity<Void> updateBouquetName(
		@DeviceUser UserContext userContext,
		@PathVariable("bouquetId") final Long bouquetId,
		@Valid @RequestBody BouquetNameUpdateRequest bouquetNameUpdateRequest
		) {
		Long memberId = userContext.id();
		bouquetCustomizingServiceV2.updateBouquetName(memberId, bouquetId, bouquetNameUpdateRequest);
		return ResponseEntity.ok().build();
	}

}
