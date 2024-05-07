package com.whoa.whoaserver.flower.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.dto.FlowerRecommandResponseDto;
import com.whoa.whoaserver.flower.dto.FlowerResponseDto;
import com.whoa.whoaserver.flower.dto.FlowerSearchResponseDto;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowerService {

    final FlowerRepository flowerRepository;

    @Transactional
    public FlowerResponseDto getFlower(final Long flowerId){
        Flower flower = flowerRepository.findByFlowerId(flowerId);
        return FlowerResponseDto.of(flower);
    }

    @Transactional
    public FlowerRecommandResponseDto getRecommendFlower(final int month, final int date){
        String recommendDate = month + "/" + date;
        Flower recommendFlower = flowerRepository.findFlowerByRecommendDate(recommendDate);
        return FlowerRecommandResponseDto.of(recommendFlower);
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
