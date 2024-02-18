package com.whoa.whoaserver.flower.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.dto.FlowerResponseDto;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public FlowerResponseDto getRecommendFlower(final int month, final int date){
        String recommendDate = month + "/" + date;
        Flower recommendFlower = flowerRepository.findByRecommendDate(recommendDate);
        return FlowerResponseDto.of(recommendFlower);
    }
}
