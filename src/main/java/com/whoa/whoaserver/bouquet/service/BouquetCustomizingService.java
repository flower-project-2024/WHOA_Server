package com.whoa.whoaserver.bouquet.service;

import org.springframework.stereotype.Service;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.domain.BouquetRepository;
import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.global.exception.BadRequestException;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.member.domain.Member;
import com.whoa.whoaserver.member.domain.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetCustomizingService {
    private final MemberRepository memberRepository;
    private final BouquetRepository bouquetRepository;
    private final Logger logger = LoggerFactory.getLogger(BouquetCustomizingService.class);

    public void registerBouquet(BouquetCustomizingRequest request, Long memberId) {
        logger.info("request colorType {}", request.colorType());
        logger.info("request wrappingType {}", request.wrappingType());

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_MEMBER));

        Bouquet bouquet = createBouquetEntity(request, member);

        bouquetRepository.save(bouquet);
    }

    private Bouquet createBouquetEntity(BouquetCustomizingRequest request, Member member) {
        return Bouquet.orderBouquet(
            member, 
            request.purpose(), 
            request.colorType(), 
            request.flowerType(), 
            request.wrappingType(), 
            request.price(), 
            request.requirement()
        );
    }
}
