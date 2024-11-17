package com.whoa.whoaserver.domain.bouquet.service;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import com.whoa.whoaserver.domain.bouquet.dto.response.RealBouquetImageResponse;
import com.whoa.whoaserver.domain.bouquet.repository.BouquetRepository;
import com.whoa.whoaserver.global.config.S3Config;
import com.whoa.whoaserver.domain.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetImageServiceV2 {

	private final BouquetRepository bouquetRepository;

	private final S3Config s3Config;
	private final BouquetCustomizingService bouquetCustomizingService;

	public RealBouquetImageResponse uploadRealBouquetMultipleFile(Long memberId, Long bouquetId, MultipartFile bouquetMultipartFile) {
		String bouquetImageS3Url = s3Config.uploadSingleFile(bouquetMultipartFile);

		final Member member = bouquetCustomizingService.getMemberByMemberId(memberId);
		final Bouquet bouquet = bouquetCustomizingService.getBouquetByMemberIdAndBouquetId(memberId, bouquetId);
		bouquetCustomizingService.validateMemberBouquetOwnership(member, bouquet);

		bouquet.registerRealBouquetImagePath(bouquetImageS3Url);
		Bouquet updatedBouquet = bouquetRepository.save(bouquet);

		return RealBouquetImageResponse.from(updatedBouquet);
	}
}
