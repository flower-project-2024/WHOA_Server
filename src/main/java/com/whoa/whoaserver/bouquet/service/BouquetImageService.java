package com.whoa.whoaserver.bouquet.service;

import java.net.URL;

import org.springframework.stereotype.Service;

import com.whoa.whoaserver.bouquet.domain.BouquetImageRepository;
import com.whoa.whoaserver.bouquet.dto.request.PresignedUrlRequest;
import com.whoa.whoaserver.global.dto.UserContext;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetImageService {
    
    private final Logger logger = LoggerFactory.getLogger(BouquetImageService.class);
    private final BouquetImageRepository bouquetImageRepository;

    public URL createPresignedUrl(UserContext userContext, PresignedUrlRequest request) {
        Long memberId = userContext.id();
        logger.info("Member ID: {}", memberId);
        throw new UnsupportedOperationException("Unimplemented method 'createPresignedUrl'");
    }
}