package com.whoa.whoaserver.bouquet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoa.whoaserver.bouquet.dto.response.BouquetInfoDetailResponse;
import com.whoa.whoaserver.global.exception.WhoaException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.bouquet.dto.response.BouquetCustomizingResponse;
import com.whoa.whoaserver.bouquet.service.BouquetCustomizingService;
import com.whoa.whoaserver.global.annotation.DeviceUser;
import com.whoa.whoaserver.global.dto.UserContext;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.whoa.whoaserver.global.exception.ExceptionCode.*;

@Tag(name = "Bouquet Customizing", description = "Header에 MEMBER_ID(key), 디바이스 등록 이후 반환 받은 id(value)로 요청해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bouquet")
public class BouquetCustomizingController {

    private final BouquetCustomizingService bouquetCustomizingService;
	private final ObjectMapper objectMapper;

    @PostMapping(value ="/customizing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "꽃다발 제작", description = "꽃다발 주문과 이미지를 등록합니다.")
    public ResponseEntity<BouquetCustomizingResponse> registerBouquet(@DeviceUser UserContext userContext,
																	  @Valid @RequestPart("request") String bouquetRequest,
																	  @RequestPart("imgUrl") List<MultipartFile> multipartFiles) {

		try {
			Long memberId = userContext.id();
			BouquetCustomizingRequest request = objectMapper.readValue(bouquetRequest, BouquetCustomizingRequest.class);
			BouquetCustomizingResponse response = bouquetCustomizingService.registerBouquet(request, memberId, multipartFiles);
			return ResponseEntity.ok(response);
		} catch (JsonProcessingException e) {
			throw new WhoaException(INVALID_BOUQUET_REQUEST_JSON_FORMAT);
		} catch (Exception e) {
			throw new WhoaException(IMAGE_UPLOAD_ERROR);
		}

    }

    @PutMapping("/customizing/{bouquetId}")
    @Operation(summary = "꽃다발 수정", description = "유저의 꽃다발 주문서와 이미지를 수정합니다.")
    public ResponseEntity<BouquetCustomizingResponse> updateBouquet(@DeviceUser UserContext userContext,
																	@Valid @RequestPart("request") String bouquetRequest,
																	@RequestPart("imgUrl") List<MultipartFile> multipartFiles,
																	@PathVariable("bouquetId") final Long bouquetId) {

		try {
			Long memberId = userContext.id();
			BouquetCustomizingRequest request = objectMapper.readValue(bouquetRequest, BouquetCustomizingRequest.class);
			BouquetCustomizingResponse response = bouquetCustomizingService.updateBouquet(request, memberId, bouquetId, multipartFiles);
			return ResponseEntity.ok(response);
		} catch (JsonProcessingException e) {
			throw new WhoaException(INVALID_BOUQUET_REQUEST_JSON_FORMAT);
		} catch (Exception e) {
			throw new WhoaException(IMAGE_UPLOAD_ERROR);
		}
    }

    @DeleteMapping("/{bouquetId}")
    @Operation(summary = "꽃다발 주문서 단건 삭제", description = "유저의 꽃다발 주문서 하나를 삭제합니다.")
    public ResponseEntity<Void> deleteBouquet(@DeviceUser UserContext userContext, @PathVariable("bouquetId") final Long bouquetId) {
        Long memberId = userContext.id();
        bouquetCustomizingService.deleteBouquet(memberId, bouquetId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Operation(summary = "꽃다발 주문서 전체 조회", description = "유저가 등록한 모든 주문서를 반환합니다.")
    public ResponseEntity<?> getAllBouquets(@DeviceUser UserContext userContext) {
        return ResponseEntity.ok().body(bouquetCustomizingService.getAllBouquets(userContext.id()));
    }

    @GetMapping("/{bouquetId}")
    @Operation(summary = "꽃다발 주문서 단건 조회", description = "유저가 등록한 주문서 한 건을 상세 조회합니다.")
    public ResponseEntity<BouquetInfoDetailResponse> getBouquetDetails(@DeviceUser UserContext userContext, @PathVariable("bouquetId") final Long bouquetId) {
        BouquetInfoDetailResponse response = bouquetCustomizingService.getBouquetDetails(userContext.id(), bouquetId);
        return ResponseEntity.ok(response);
    }
}
