package com.whoa.whoaserver.flower.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flower.dto.FlowerPostResponseDto;
import com.whoa.whoaserver.flower.dto.FlowerRecommendResponseDto;
import com.whoa.whoaserver.flower.dto.FlowerResponseDto;
import com.whoa.whoaserver.flower.dto.FlowerSearchResponseDto;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.repository.FlowerExpressionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowerService {

    final FlowerRepository flowerRepository;
    final FlowerExpressionRepository flowerExpressionRepository;
    final S3Uploader s3Uploader;

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
    public FlowerResponseDto getFlower(final Long flowerId){
        Flower flower = flowerRepository.findByFlowerId(flowerId);
        return FlowerResponseDto.of(flower);
    }

    @Transactional
    public FlowerRecommendResponseDto getRecommendFlower(final int month, final int date){
        String recommendDate = month + "/" + date;
        Flower recommendFlower = null;
        Optional<Flower> recommendAcceptFlower = flowerRepository.findFlowerByRecommendDate(recommendDate);
        if (recommendAcceptFlower.isPresent())
            recommendFlower = recommendAcceptFlower.get();
        else {
            int flowerCount = (int) flowerRepository.count();
            Random random = new Random((month * 31) + date);
            int randomIndex = Math.abs(random.nextInt() % flowerCount);
            recommendFlower = flowerRepository.findAll().get(randomIndex);
        }
        return FlowerRecommendResponseDto.of(recommendFlower);
    }

    @Transactional
    public List<FlowerSearchResponseDto> getAllFlowers() {
        List<Flower> flowers = flowerRepository.findAll();

        return flowers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FlowerSearchResponseDto convertToDto(Flower flower) {
        return new FlowerSearchResponseDto(
                flower.getFlowerId(),
                flower.getFlowerName()
        );
    }
}
