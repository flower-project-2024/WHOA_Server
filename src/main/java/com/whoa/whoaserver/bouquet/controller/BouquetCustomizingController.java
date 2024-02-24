package com.whoa.whoaserver.bouquet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.bouquet.service.BouquetCustomizingService;
import com.whoa.whoaserver.global.annotation.DeviceUser;
import com.whoa.whoaserver.global.dto.UserContext;
import com.whoa.whoaserver.global.dto.UserContext;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Bouquet Customizing", description = "Bouquet Customizing API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bouquet")
public class BouquetCustomizingController {

    private final BouquetCustomizingService bouquetCustomizingService;
    private final Logger logger = LoggerFactory.getLogger(BouquetCustomizingController.class);


    @PostMapping("/customizing")
    @Operation(summary = "꽃다발 제작", description = "꽃다발 주문 등록")

    public ResponseEntity<String> registerBouquet(@DeviceUser UserContext userContext, @Valid @RequestBody BouquetCustomizingRequest request) { 
        Long memberId = userContext.id();
        logger.info("member id : {} ", memberId);
        bouquetCustomizingService.registerBouquet(request, memberId);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    
}
