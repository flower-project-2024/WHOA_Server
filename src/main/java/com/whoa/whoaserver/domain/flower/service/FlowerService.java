package com.whoa.whoaserver.domain.flower.service;

import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerPostResponseDto;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerRecommendResponseDto;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerResponseDto;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerSearchResponseDto;
import com.whoa.whoaserver.domain.flower.domain.FlowerImage;
import com.whoa.whoaserver.domain.flower.repository.FlowerRepository;
import com.whoa.whoaserver.global.config.type.CacheType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FlowerService {

	private final FlowerRepository flowerRepository;
	private final FlowerDataUploader s3Uploader;

	@Transactional
	public FlowerPostResponseDto postFlower(final List<MultipartFile> flowerImages, final Long flowerId) throws IOException {
		Flower flower = flowerRepository.findByFlowerId(flowerId);
		List<FlowerImage> storedFlowerImages = new ArrayList<>();
		for (MultipartFile flowerImage : flowerImages) {
			String storedFileName = s3Uploader.saveFileExceptUser(flowerImage, "flower");
			FlowerImage flowerImageEntity = FlowerImage.create(storedFileName, flower);
			storedFlowerImages.add(flowerImageEntity);
		}
		flower.getFlowerImages().addAll(storedFlowerImages);
		return FlowerPostResponseDto.of(flower);
	}

	@Transactional
	public FlowerResponseDto getFlower(final Long flowerId) {
		Flower flower = flowerRepository.findByFlowerId(flowerId);
		return FlowerResponseDto.of(flower);
	}

	@Transactional
	public FlowerRecommendResponseDto getRecommendFlower(final int month, final int date) {
		String recommendDate = month + "/" + date;

		Flower recommendFlower = flowerRepository.findFlowerByRecommendDate(recommendDate)
			.orElseGet(flowerRepository::findRandomFlower);

		return FlowerRecommendResponseDto.of(recommendFlower);
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = CacheType.FLOWER_SEARCH_CACHE_NAME)
	public List<FlowerSearchResponseDto> getAllFlowers() {
		return flowerRepository.findAllFlowers();
	}
}
