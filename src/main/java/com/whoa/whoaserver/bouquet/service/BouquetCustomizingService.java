package com.whoa.whoaserver.bouquet.service;

import com.whoa.whoaserver.bouquet.dto.response.BouquetInfoDetailResponse;
import com.whoa.whoaserver.bouquet.dto.response.BouquetOrderResponse;
import com.whoa.whoaserver.flower.utils.FlowerUtils;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.repository.FlowerExpressionRepository;
import org.springframework.stereotype.Service;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.repository.BouquetRepository;
import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.bouquet.dto.response.BouquetCustomizingResponse;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.member.domain.Member;
import com.whoa.whoaserver.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetCustomizingService {
	private final MemberRepository memberRepository;
	private final BouquetRepository bouquetRepository;
	private final FlowerExpressionRepository flowerExpressionRepository;

	public BouquetCustomizingResponse registerBouquet(BouquetCustomizingRequest request, Long memberId) {

		Member member = getMemberByMemberId(memberId);

		Optional<Bouquet> existingBouquetOptional = bouquetRepository.findByMemberIdAndBouquetName(memberId, request.bouquetName());
		if (existingBouquetOptional.isPresent()) {
			throw new WhoaException(DUPLICATED_BOUQUET_NAME);
		}

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
			request.colorName(),
			request.pointColor(),
			request.flowerType(),
			request.substitutionType(),
			request.wrappingType(),
			request.price(),
			request.requirement()
		);
	}


	public BouquetCustomizingResponse updateBouquet(BouquetCustomizingRequest request, Long memberId, Long bouquetId) {
		Member member = getMemberByMemberId(memberId);

		Bouquet existingBouquet = getBouquetByMemberIdAndBouquetId(memberId, bouquetId);

		validateMemberBouquetOwnership(member, existingBouquet);

		existingBouquet.changeBouquet(
			request.bouquetName(),
			request.purpose(),
			request.colorType(),
			request.colorName(),
			request.pointColor(),
			request.flowerType(),
			request.substitutionType(),
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

		validateMemberBouquetOwnership(member, bouquetToDelete);

		member.getBouquet().remove(bouquetToDelete);
		bouquetRepository.delete(bouquetToDelete);
	}

	public void validateMemberBouquetOwnership(Member member, Bouquet bouquet) {
		if (!bouquet.getMember().equals(member)) {
			throw new WhoaException(NOT_MEMBER_BOUQUET);
		}
	}

	public List<BouquetOrderResponse> getAllBouquets(Long memberId) {
		List<Bouquet> memberBouquets = bouquetRepository.findAllByMemberId(memberId);

		return memberBouquets.stream()
			.map(bouquet -> new BouquetOrderResponse(
					bouquet.getId(),
					bouquet.getBouquetName(),
					bouquet.getCreatedAt().toString().substring(0, 10),
					getOneSelectedFlowerFromBouquet(bouquet)
				)
			)
			.collect(Collectors.toUnmodifiableList());
	}

	public BouquetInfoDetailResponse getBouquetDetails(Long memberId, Long bouquetId) {
		Bouquet bouquetToRead = bouquetRepository.findByMemberIdAndId(memberId, bouquetId)
			.orElseThrow(() -> new WhoaException(NOT_REGISTER_BOUQUET));

		List<String> flowerExpressionStringIdsList = FlowerUtils.parseFlowerEnumerationColumn(bouquetToRead.getFlowerType());

		List<Long> flowerExpressionLongIdsList = flowerExpressionStringIdsList.stream()
			.map(id -> Long.parseLong(id))
			.collect(Collectors.toUnmodifiableList());

		List<FlowerExpression> flowerExpressionList = flowerExpressionLongIdsList.stream()
			.map(id -> flowerExpressionRepository.findByFlowerExpressionId(id))
			.collect(Collectors.toUnmodifiableList());

		return BouquetInfoDetailResponse.of(bouquetToRead, flowerExpressionList);
	}

	public Member getMemberByMemberId(Long memberId) {
		Member targetMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new WhoaException(INVALID_MEMBER));

		return targetMember;
	}

	public Bouquet getBouquetByMemberIdAndBouquetId(Long memberId, Long bouquetId) {
		Bouquet targetBouquet = bouquetRepository.findByMemberIdAndId(memberId, bouquetId)
			.orElseThrow(() -> new WhoaException(NOT_REGISTER_BOUQUET));

		return targetBouquet;
	}

	private List<String> getOneSelectedFlowerFromBouquet(Bouquet eachBouquet) {
		List<String> flowerTypes = FlowerUtils.parseFlowerEnumerationColumn(eachBouquet.getFlowerType());
		Long selectedFlowerExpressionId = Long.parseLong(flowerTypes.get(0));

		FlowerExpression selectedFlowerExpression = flowerExpressionRepository.findByFlowerExpressionId(selectedFlowerExpressionId);

		return List.of(selectedFlowerExpression.getFlowerImage().getImageUrl());
	}


}
