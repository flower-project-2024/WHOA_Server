package com.whoa.whoaserver.domain.bouquet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import com.whoa.whoaserver.domain.bouquet.domain.BouquetImage;
import com.whoa.whoaserver.domain.bouquet.domain.type.BouquetStatus;
import com.whoa.whoaserver.domain.bouquet.dto.request.BouquetCustomizingRequest;
import com.whoa.whoaserver.domain.bouquet.dto.request.BouquetNameUpdateRequest;
import com.whoa.whoaserver.domain.bouquet.dto.response.BouquetCustomizingResponseV2;
import com.whoa.whoaserver.domain.bouquet.dto.response.BouquetInfoDetailResponseV2;
import com.whoa.whoaserver.domain.bouquet.dto.response.BouquetOrderResponseV2;
import com.whoa.whoaserver.domain.bouquet.repository.BouquetImageRepository;
import com.whoa.whoaserver.domain.bouquet.repository.BouquetRepository;
import com.whoa.whoaserver.domain.flower.utils.FlowerUtils;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.domain.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.domain.flowerExpression.service.FlowerExpressionService;
import com.whoa.whoaserver.global.config.S3Config;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.domain.member.domain.Member;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.*;
import static com.whoa.whoaserver.global.utils.LoggerUtils.logger;
import static com.whoa.whoaserver.global.utils.ClientUtils.getClientIP;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetCustomizingServiceV2 {
	public static final String DEFAULT_WRAPPING_TYPE = "아니요, 사장님께 맡길게요";

	private final BouquetRepository bouquetRepository;
	private final BouquetImageRepository bouquetImageRepository;
	private final FlowerExpressionRepository flowerExpressionRepository;
	private final BouquetCustomizingService bouquetCustomizingService;
	private final FlowerExpressionService flowerExpressionService;
	private final S3Config s3Config;
	private final ObjectMapper objectMapper;

	public BouquetCustomizingResponseV2 registerBouquet(BouquetCustomizingRequest request, Long memberId, List<MultipartFile> multipartFiles) {

		Member member = bouquetCustomizingService.getMemberByMemberId(memberId);

		Bouquet newBouquet = createBouquetEntity(request, member);
		if (newBouquet.getWrappingType() == null || newBouquet.getWrappingType().isBlank()) {
			newBouquet.initializeBouquetWrappingType(DEFAULT_WRAPPING_TYPE);
		}

		Bouquet savedBouquet = bouquetRepository.save(newBouquet);

		List<String> imgPaths = getImagePathsFromMultipartContent(memberId, savedBouquet, multipartFiles);

		return BouquetCustomizingResponseV2.of(savedBouquet, imgPaths);
	}

	private List<String> getImagePathsFromMultipartContent(Long memberId, Bouquet targetBouquet, List<MultipartFile> multipartFiles) {
		if (!multipartFiles.isEmpty()) {
			return handleMultipartFiles(memberId, targetBouquet, multipartFiles);
		} else {
			return Collections.emptyList();
		}
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
			request.requirement());
	}

	private List<String> handleMultipartFiles(Long memberId, Bouquet bouquet, List<MultipartFile> multipartFiles) {
		List<String> imgPaths = s3Config.upload(multipartFiles);
		saveMultipleFilesUrlWithBouquetAtOnce(memberId, imgPaths, bouquet.getId());

		return imgPaths;
	}

	private void saveMultipleFilesUrlWithBouquetAtOnce(Long memberId, List<String> imgPaths, Long bouquetId) {
		String clientIP = getClientIP();
		BouquetCustomizingResponseV2 clientRequest = new BouquetCustomizingResponseV2(bouquetId, imgPaths);

		try {
			String jsonString = objectMapper.writeValueAsString(clientRequest);
			Bouquet bouquetWithImg = bouquetRepository.findByBouquetIdWithMember(memberId, bouquetId)
				.orElseThrow(() -> new WhoaException(
					ExceptionCode.NOT_REGISTER_BOUQUET,
					"saveMultipleFilesUrlWithBouquetAtOnce - s3에 bouquet 이미지 업로드 이후 BouquetImage와 매핑할 Bouquet findBy 에러 발생",
					clientIP,
					jsonString
				));

			for (String imgUrl : imgPaths) {
				BouquetImage bouquetImage = BouquetImage.create(bouquetWithImg, imgUrl);
				bouquetImageRepository.save(bouquetImage);
			}
		} catch (JsonProcessingException e) {
			throw new WhoaException(
				ExceptionCode.OBJECT_MAPPER_JSON_PROCESSING_ERROR,
				"saveMultipleFilesUrlWithBouquetAtOnce - clientRequest dto를 json string으로 전환하면서 오류 발생"
			);
		}
	}

	@Transactional
	public BouquetCustomizingResponseV2 updateBouquet(BouquetCustomizingRequest request, Long memberId, Long bouquetId, List<MultipartFile> multipartFiles) {
		String clientIP = getClientIP();
		Member member = bouquetCustomizingService.getMemberByMemberId(memberId);

		Bouquet existingBouquet = bouquetCustomizingService.getBouquetByMemberIdAndBouquetId(memberId, bouquetId);

		try {
			String jsonString = objectMapper.writeValueAsString(existingBouquet);
			if (!existingBouquet.getMember().equals(member)) {
				throw new WhoaException(
					NOT_MEMBER_BOUQUET,
					"updateBouquet - 실제 bouquet을 수정 요청을 한 memberId와 bouquet의 memberId가 불일치",
					clientIP,
					"memberId request : " + memberId + "\n" + "bouquet request : " + jsonString
				);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new WhoaException(
				ExceptionCode.OBJECT_MAPPER_JSON_PROCESSING_ERROR,
				"updateBouquet - pathVariable로 받은 bouquetId와 controller memberId로 찾은 bouquet 객체를 json string으로 전환하면서 오류 발생"
			);
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
			request.requirement());

		if (request.wrappingType() == null || request.wrappingType().isBlank()) {
			existingBouquet.initializeBouquetWrappingType(DEFAULT_WRAPPING_TYPE);
		}

		Bouquet savedBouquet = bouquetRepository.save(existingBouquet);

		List<BouquetImage> existingBouquetImages = bouquetImageRepository.findAllByBouquet(existingBouquet);
		bouquetImageRepository.deleteAll(existingBouquetImages);

		List<String> imgPaths = getImagePathsFromMultipartContent(memberId, savedBouquet, multipartFiles);

		return BouquetCustomizingResponseV2.of(existingBouquet, imgPaths);
	}

	public void updateBouquetStatus(Long memberId, Long bouquetId) {
		Member member = bouquetCustomizingService.getMemberByMemberId(memberId);

		Bouquet bouquetToUpdate = bouquetCustomizingService.getBouquetByMemberIdAndBouquetId(memberId, bouquetId);

		bouquetCustomizingService.validateMemberBouquetOwnership(member, bouquetToUpdate);

		bouquetToUpdate.updateBouquetStatus(BouquetStatus.COMPLETED);

		bouquetRepository.save(bouquetToUpdate);
	}

	public List<BouquetOrderResponseV2> getAllBouquetsWithStatus(Long memberId) {
		List<Bouquet> memberBouquets = bouquetRepository.findAllByMemberIdOrderByIdDesc(memberId);

		return memberBouquets.stream()
			.map(bouquet -> new BouquetOrderResponseV2(
				bouquet.getId(),
				bouquet.getBouquetName(),
				bouquet.getCreatedAt().toString().substring(0, 10),
				getAllSelectedFlowerImagesFromBouquet(bouquet),
				bouquet.getRealImageUrl(),
				bouquet.getBouquetStatus().getValue()
			))
			.collect(Collectors.toUnmodifiableList());
	}

	private List<String> getAllSelectedFlowerImagesFromBouquet(Bouquet eachBouquet) {
		List<String> flowerTypes = FlowerUtils.parseFlowerEnumerationColumn(eachBouquet.getFlowerType());

		List<Long> flowerTypeIds = flowerTypes.stream().map(Long::valueOf).collect(Collectors.toUnmodifiableList());

		return flowerTypeIds.stream()
			.peek(flowerExpressionId -> {
				if (flowerExpressionId == null) {
					logger.debug("flowerExpressionId is null -> cacheable key null");
				} else {
					logger.info("flowerExpressionId: {}", flowerExpressionId);
				}
			})
			.filter(Objects::nonNull)
			.map(flowerExpressionService::getFlowerImageUrlByFlowerExpressionId)
			.collect(Collectors.toUnmodifiableList());
	}

	public BouquetInfoDetailResponseV2 getBouquetDetails(Long memberId, Long bouquetId) {
		String clientIP = getClientIP();
		Bouquet bouquetToRead = bouquetRepository.findByMemberIdAndId(memberId, bouquetId)
			.orElseThrow(() -> new WhoaException(
				NOT_REGISTER_BOUQUET,
				"getBouquetDetails - pathVariable bouquetId와 controller memberId로 bouquet findBy할 때 찾을 수 있는 객체가 없음",
				clientIP,
				"memberId request : " + memberId + ", bouquetId request : " + bouquetId
			));

		List<String> flowerExpressionStringIdsList = FlowerUtils.parseFlowerEnumerationColumn(bouquetToRead.getFlowerType());

		List<FlowerExpression> flowerExpressionList = flowerExpressionStringIdsList.stream()
			.map(Long::parseLong)
			.map(flowerExpressionRepository::findByFlowerExpressionId)
			.collect(Collectors.toUnmodifiableList());

		return BouquetInfoDetailResponseV2.of(bouquetToRead, flowerExpressionList);
	}

	public void updateBouquetName(Long memberId, Long bouquetId, BouquetNameUpdateRequest request) {
		Bouquet existingBouquet = bouquetCustomizingService.getBouquetByMemberIdAndBouquetId(memberId, bouquetId);
		existingBouquet.updateBouquetName(request.bouquetName());
		bouquetRepository.save(existingBouquet);
	}
}
