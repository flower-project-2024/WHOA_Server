package com.whoa.whoaserver.bouquet.service;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.domain.BouquetImage;
import com.whoa.whoaserver.bouquet.domain.type.BouquetStatus;
import com.whoa.whoaserver.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.bouquet.dto.response.BouquetCustomizingResponseV2;
import com.whoa.whoaserver.bouquet.dto.response.BouquetOrderResponse;
import com.whoa.whoaserver.bouquet.repository.BouquetImageRepository;
import com.whoa.whoaserver.bouquet.repository.BouquetRepository;
import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flower.utils.FlowerUtils;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.global.config.S3Config;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.DUPLICATED_BOUQUET_NAME;
import static com.whoa.whoaserver.global.exception.ExceptionCode.NOT_MEMBER_BOUQUET;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetCustomizingServiceV2 {

	private final BouquetRepository bouquetRepository;
	private final BouquetImageRepository bouquetImageRepository;
	private final FlowerExpressionRepository flowerExpressionRepository;
	private final BouquetCustomizingService bouquetCustomizingService;
	private final S3Config s3Config;

	public BouquetCustomizingResponseV2 registerBouquet(BouquetCustomizingRequest request, Long memberId, List<MultipartFile> multipartFiles) {

		Member member = bouquetCustomizingService.getMemberByMemberId(memberId);

		Optional<Bouquet> existingBouquetOptional = bouquetRepository.findByMemberIdAndBouquetName(memberId, request.bouquetName());

		if (existingBouquetOptional.isPresent()) {
			throw new WhoaException(DUPLICATED_BOUQUET_NAME);
		}

		Bouquet newBouquet = createBouquetEntity(request, member);

		bouquetRepository.save(newBouquet);

		List<String> imgPaths = handleMultipartFiles(memberId, newBouquet, multipartFiles);

		return BouquetCustomizingResponseV2.of(newBouquet, imgPaths);
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

	private List<String> handleMultipartFiles(Long memberId, Bouquet bouquet, List<MultipartFile> multipartFiles) {
		List<String> imgPaths = s3Config.upload(multipartFiles);
		saveMultipleFilesUrlWithBouquetAtOnce(memberId, imgPaths, bouquet.getId());

		return imgPaths;
	}

	private void saveMultipleFilesUrlWithBouquetAtOnce(Long memberId, List<String> imgPaths, Long bouquetId) {

		Bouquet bouquetWithImg = bouquetRepository.findByBouquetIdWithMember(memberId, bouquetId)
			.orElseThrow(() -> new WhoaException(ExceptionCode.NOT_REGISTER_BOUQUET));

		for (String imgUrl : imgPaths) {
			BouquetImage bouquetImage = BouquetImage.create(bouquetWithImg, imgUrl);
			bouquetImageRepository.save(bouquetImage);
		}
	}

	public BouquetCustomizingResponseV2 updateBouquet(BouquetCustomizingRequest request, Long memberId, Long bouquetId, List<MultipartFile> multipartFiles) {
		Member member = bouquetCustomizingService.getMemberByMemberId(memberId);

		Bouquet existingBouquet = bouquetCustomizingService.getBouquetByMemberIdAndBouquetId(memberId, bouquetId);

		if (!existingBouquet.getMember().equals(member)) {
			throw new WhoaException(NOT_MEMBER_BOUQUET);
		}

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

		List<BouquetImage> existingBouquetImages = bouquetImageRepository.findAllByBouquet(existingBouquet);
		bouquetImageRepository.deleteAll(existingBouquetImages);

		List<String> imgPaths = handleMultipartFiles(memberId, existingBouquet, multipartFiles);

		return BouquetCustomizingResponseV2.of(existingBouquet, imgPaths);
	}

	public void updateBouquetStatus(Long memberId, Long bouquetId) {
		Member member = bouquetCustomizingService.getMemberByMemberId(memberId);

		Bouquet bouquetToUpdate = bouquetCustomizingService.getBouquetByMemberIdAndBouquetId(memberId, bouquetId);

		bouquetCustomizingService.validateMemberBouquetOwnership(member, bouquetToUpdate);

		bouquetToUpdate.updateBouquetStatus(BouquetStatus.COMPLETED);
	}

	public Map<String, List<BouquetOrderResponse>> getAllBouquetsByBouquetStatus(Long memberId) {
		Map<String, List<BouquetOrderResponse>> totalResponse = new HashMap<>();

		putBouquetOrderResponseListByBouquetStatus(memberId, BouquetStatus.INCOMPLETED, totalResponse);
		putBouquetOrderResponseListByBouquetStatus(memberId, BouquetStatus.COMPLETED, totalResponse);

		return totalResponse;
	}

	private void putBouquetOrderResponseListByBouquetStatus(Long memberId, BouquetStatus bouquetStatus, Map<String, List<BouquetOrderResponse>> totalResponse) {
		List<Bouquet> allBouquetsByStatus = bouquetRepository.findAllByMemberIdAndBouquetStatus(memberId, bouquetStatus);
		List<BouquetOrderResponse> bouquetResponsesByStatus = allBouquetsByStatus.stream()
			.map(bouquet -> new BouquetOrderResponse(
				bouquet.getId(),
				bouquet.getBouquetName(),
				bouquet.getCreatedAt().toString().substring(0, 10),
				getAllSelectedFlowerFromBouquet(bouquet))
			)
			.collect(Collectors.toUnmodifiableList());

		totalResponse.put(bouquetStatus.getValue(), bouquetResponsesByStatus);
	}

	private List<String> getAllSelectedFlowerFromBouquet(Bouquet eachBouquet) {
		List<String> flowerTypes = FlowerUtils.parseFlowerEnumerationColumn(eachBouquet.getFlowerType());

		List<Long> flowerTypeIds = flowerTypes.stream()
			.map(Long::valueOf)
			.collect(Collectors.toUnmodifiableList());

		return flowerTypeIds.stream()
			.map(flowerExpressionRepository::findByFlowerExpressionId)
			.map(FlowerExpression::getFlowerImage)
			.map(FlowerImage::getImageUrl)
			.collect(Collectors.toUnmodifiableList());
	}
}
