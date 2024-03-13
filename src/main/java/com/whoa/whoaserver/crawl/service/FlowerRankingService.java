package com.whoa.whoaserver.crawl.service;


import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import com.whoa.whoaserver.crawl.dto.FlowerRankingResponseDto;
import com.whoa.whoaserver.crawl.repository.FlowerRankingRepository;
import com.whoa.whoaserver.flower.dto.FlowerResponseDto;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlowerRankingService {

    final FlowerRankingRepository flowerRankingRepository;
    final FlowerRepository flowerRepository;

    @Transactional
    public FlowerRankingResponseDto saveFlowerRanking(final Long flowerRankingId, final String pumName, final String avgAmt, final String date) {
        FlowerRanking flowerRanking = flowerRankingRepository.findByFlowerRankingId(flowerRankingId);
        Optional<String> flowerRankingDescription = flowerRepository.findFlowerDescriptionByFlowerName(pumName);
        if (flowerRankingDescription.isPresent()){
            flowerRanking.update(pumName, String.valueOf(flowerRankingDescription), avgAmt, date);
            return new FlowerRankingResponseDto(flowerRanking.getFlowerRankingId(), flowerRanking.getFlowerRankingName(), flowerRanking.getFlowerRankingDescription(), flowerRanking.getFlowerRankingPrize(), flowerRanking.getFlowerRankingDate());
        } else{
            flowerRanking.update(pumName, null, avgAmt, date);
            return new FlowerRankingResponseDto(flowerRanking.getFlowerRankingId(), flowerRanking.getFlowerRankingName(), flowerRanking.getFlowerRankingDescription(), flowerRanking.getFlowerRankingPrize(), flowerRanking.getFlowerRankingDate());
        }
    }

    @Transactional
    public List<FlowerRankingResponseDto> getFlowerRanking(){
        List<FlowerRankingResponseDto> flowerRankings = new ArrayList<>();
        for (long i=0; i<3; i++){
            FlowerRanking flowerRankingOne = flowerRankingRepository.findByFlowerRankingId(i+1);
            FlowerRankingResponseDto flowerRankingResponseDtoOne = new FlowerRankingResponseDto(flowerRankingOne.getFlowerRankingId(), flowerRankingOne.getFlowerRankingName(), flowerRankingOne.getFlowerRankingDescription(), flowerRankingOne.getFlowerRankingPrize(), flowerRankingOne.getFlowerRankingDate());
            flowerRankings.add(flowerRankingResponseDtoOne);
        }
        return flowerRankings;
    }

}
