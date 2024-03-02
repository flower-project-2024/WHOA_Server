package com.whoa.whoaserver.bouquet.service;

import com.whoa.whoaserver.bouquet.dto.response.BouquetOrderResponse;
import org.springframework.stereotype.Service;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.domain.BouquetRepository;
import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.bouquet.dto.response.BouquetCustomizingResponse;
import com.whoa.whoaserver.global.exception.BadRequestException;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.member.domain.Member;
import com.whoa.whoaserver.member.domain.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetCustomizingService {
    private final MemberRepository memberRepository;
    private final BouquetRepository bouquetRepository;

    public BouquetCustomizingResponse registerBouquet(BouquetCustomizingRequest request, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(INVALID_MEMBER));

        Bouquet bouquet = createBouquetEntity(request, member);

        bouquetRepository.save(bouquet);

        return BouquetCustomizingResponse.of(bouquet);
    }

    private Bouquet createBouquetEntity(BouquetCustomizingRequest request, Member member) {
        return Bouquet.orderBouquet(
            member, 
            request.purpose(),
            request.colorType(), 
            request.flowerType(), 
            request.wrappingType(), 
            request.price(), 
            request.requirement(),
            request.imgPath()
        );
    }

    public void deleteBouquet(Long memberId, Long bouquetId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(INVALID_MEMBER));

        Bouquet bouquetToDelete = bouquetRepository.findByMemberIdAndId(memberId, bouquetId)
                .orElseThrow(() -> new BadRequestException(NOT_REGISTER_BOUQUET));

        if (!bouquetToDelete.getMember().equals(member)) {
            throw new BadRequestException(NOT_MEMBER_BOUQUET);
        }

        member.getBouquet().remove(bouquetToDelete);
        bouquetRepository.delete(bouquetToDelete);
    }

    public List<BouquetOrderResponse> getAllBouquets(Long memberId) {
        List<Bouquet> memberBouquets = bouquetRepository.findByMemberId(memberId).orElseThrow(() -> new BadRequestException(NOT_REGISTER_BOUQUET));

        return memberBouquets.stream()
                .map(bouquet -> new BouquetOrderResponse(bouquet.getId(), bouquet.getImagePath()))
                .collect(Collectors.toList());
    }
}
