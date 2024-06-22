package com.whoa.whoaserver.flower.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flower.dto.FlowerRecommendResponseDto;
import com.whoa.whoaserver.flower.dto.FlowerResponseDto;
import com.whoa.whoaserver.flower.dto.FlowerSearchResponseDto;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowerService {

    final FlowerRepository flowerRepository;
    final S3Uploader s3Uploader;

    @Transactional
    public FlowerResponseDto postFlower(final Flower flower){
        flowerRepository.save(flower);
        return FlowerResponseDto.of(flower);
    }

    @Transactional
    public FlowerResponseDto getFlower(final Long flowerId){
        Flower flower = flowerRepository.findByFlowerId(flowerId);
        return FlowerResponseDto.of(flower);
    }

    @Transactional
    public FlowerRecommendResponseDto getRecommendFlower(final int month, final int date){
        String recommendDate = month + "/" + date;
        Flower recommendFlower = flowerRepository.findFlowerByRecommendDate(recommendDate);
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
