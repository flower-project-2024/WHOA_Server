package com.whoa.whoaserver.bouquet.service;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.domain.BouquetImage;
import com.whoa.whoaserver.bouquet.dto.response.RealBouquetImageResponse;
import com.whoa.whoaserver.bouquet.repository.BouquetImageRepository;
import com.whoa.whoaserver.global.config.S3Config;
import com.whoa.whoaserver.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetImageServiceV2 {

	private final BouquetImageRepository bouquetImageRepository;

	private final S3Config s3Config;
	private final BouquetCustomizingService bouquetCustomizingService;

	public RealBouquetImageResponse uploadRealBouquetMultipleFile(Long memberId, Long bouquetId, MultipartFile bouquetMultipartFile) {
		String bouquetImageS3Url = s3Config.uploadSingleFile(bouquetMultipartFile);

		final Member member = bouquetCustomizingService.getMemberByMemberId(memberId);
		final Bouquet bouquet = bouquetCustomizingService.getBouquetByMemberIdAndBouquetId(memberId, bouquetId);
		bouquetCustomizingService.validateMemberBouquetOwnership(member, bouquet);

		List<BouquetImage> bouquetImages = bouquetImageRepository.findAllByBouquet(bouquet);
		bouquetImageRepository.deleteAll(bouquetImages);

		BouquetImage newBouquetRealImage = BouquetImage.create(bouquet, bouquetImageS3Url);
		bouquetImageRepository.save(newBouquetRealImage);

		return RealBouquetImageResponse.from(newBouquetRealImage);
	}
}
