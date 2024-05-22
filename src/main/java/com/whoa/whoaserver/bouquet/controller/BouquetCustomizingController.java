package com.whoa.whoaserver.bouquet.controller;

import com.whoa.whoaserver.bouquet.dto.response.BouquetInfoDetailResponse;
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

@Tag(name = "Bouquet Customizing", description = "Header에 MEMBER_ID(key), 디바이스 등록 이후 반환 받은 id(value)로 요청해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bouquet")
public class BouquetCustomizingController {

    private final BouquetCustomizingService bouquetCustomizingService;

    @PostMapping("/customizing")
    @Operation(summary = "꽃다발 제작", description = "꽃다발 주문을 등록합니다.")
    public ResponseEntity<BouquetCustomizingResponse> registerBouquet(@DeviceUser UserContext userContext, @Valid @RequestBody BouquetCustomizingRequest request) { 
        Long memberId = userContext.id();
        BouquetCustomizingResponse response = bouquetCustomizingService.registerBouquet(request, memberId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customizing/{bouquetId}")
    @Operation(summary = "꽃다발 수정", description = "유저의 꽃다발 주문서를 수정합니다.")
    public ResponseEntity<BouquetCustomizingResponse> updateBouquet(@DeviceUser UserContext userContext, @Valid @RequestBody BouquetCustomizingRequest request, @PathVariable("bouquetId") final Long bouquetId) {
        Long memberId = userContext.id();
        BouquetCustomizingResponse response = bouquetCustomizingService.updateBouquet(request, memberId, bouquetId);
        return ResponseEntity.ok(response);
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
