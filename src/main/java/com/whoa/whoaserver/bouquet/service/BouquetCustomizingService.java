package com.whoa.whoaserver.bouquet.service;

import com.whoa.whoaserver.bouquet.dto.response.BouquetInfoDetailResponse;
import com.whoa.whoaserver.bouquet.dto.response.BouquetOrderResponse;
import org.springframework.stereotype.Service;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.domain.BouquetRepository;
import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.bouquet.dto.response.BouquetCustomizingResponse;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.member.domain.Member;
import com.whoa.whoaserver.member.domain.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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

        Member member = getMemberByMemberId(memberId);

        Bouquet bouquet = createBouquetEntity(request, member);

        bouquetRepository.save(bouquet);

        return BouquetCustomizingResponse.of(bouquet);
    }

    private Bouquet createBouquetEntity(BouquetCustomizingRequest request, Member member) {
        return Bouquet.orderBouquet(
            member,
            request.bouquetName(),
            request.purpose(),
            request.colorType(), 
            request.flowerType(), 
            request.wrappingType(), 
            request.price(), 
            request.requirement()
        );
    }

    public BouquetCustomizingResponse updateBouquet(BouquetCustomizingRequest request, Long memberId, Long bouquetId) {
        Member member = getMemberByMemberId(memberId);

        Bouquet existingBouquet = getBouquetByMemberIdAndBouquetId(memberId, bouquetId);

        if (!existingBouquet.getMember().equals(member)) {
            throw new WhoaException(NOT_MEMBER_BOUQUET);
        }

        existingBouquet.changeBouquet(
                request.bouquetName(),
                request.purpose(),
                request.colorType(),
                request.flowerType(),
                request.wrappingType(),
                request.price(),
                request.requirement()
        );

        bouquetRepository.save(existingBouquet);

        return BouquetCustomizingResponse.of(existingBouquet);
    }

    public void deleteBouquet(Long memberId, Long bouquetId) {
        Member member = getMemberByMemberId(memberId);

        Bouquet bouquetToDelete = getBouquetByMemberIdAndBouquetId(memberId, bouquetId);

        if (!bouquetToDelete.getMember().equals(member)) {
            throw new WhoaException(NOT_MEMBER_BOUQUET);
        }

        member.getBouquet().remove(bouquetToDelete);
        bouquetRepository.delete(bouquetToDelete);
    }

    public List<BouquetOrderResponse> getAllBouquets(Long memberId) {
        List<Bouquet> memberBouquets = bouquetRepository.findByMemberId(memberId)
                .orElseThrow(() -> new WhoaException(NOT_REGISTER_BOUQUET));

        return memberBouquets.stream()
                .map(bouquet -> new BouquetOrderResponse(bouquet.getId(), bouquet.getBouquetName(), bouquet.getImages().stream()
                        .map(bouquetImage -> bouquetImage.getFileName())
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public BouquetInfoDetailResponse getBouquetDetails(Long memberId, Long bouquetId) {
        Bouquet bouquetToRead = bouquetRepository.findByMemberIdAndId(memberId, bouquetId)
                .orElseThrow(() -> new WhoaException(NOT_REGISTER_BOUQUET));

        return BouquetInfoDetailResponse.of(bouquetToRead);
    }

    private Member getMemberByMemberId(Long memberId) {
        Member targetMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new WhoaException(INVALID_MEMBER));
        return targetMember;
    }

    private Bouquet getBouquetByMemberIdAndBouquetId(Long memberId, Long bouquetId) {
        Bouquet targetBouquet = bouquetRepository.findByMemberIdAndId(memberId, bouquetId)
                .orElseThrow(() -> new WhoaException(NOT_REGISTER_BOUQUET));
        return targetBouquet;
    }


}
