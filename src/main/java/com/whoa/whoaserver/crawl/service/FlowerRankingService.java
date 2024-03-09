package com.whoa.whoaserver.crawl.service;


import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import com.whoa.whoaserver.crawl.dto.FlowerRankingResponseDto;
import com.whoa.whoaserver.crawl.repository.FlowerRankingRepository;
import com.whoa.whoaserver.flower.dto.FlowerResponseDto;
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

    @Transactional
    public FlowerRankingResponseDto saveFlowerRanking(final Long flowerRankingId, final String pumName, final String avgAmt) {
        FlowerRanking flowerRanking = flowerRankingRepository.findByFlowerRankingId(flowerRankingId);
        Optional<String> flowerRankingDescription = flowerRankingRepository.findFlowerDescriptionByFlowerName(pumName);
        if (flowerRankingDescription.isPresent()){
            flowerRanking.update(pumName, String.valueOf(flowerRankingDescription), avgAmt);
            return new FlowerRankingResponseDto(flowerRanking.getFlowerRankingId(), flowerRanking.getFlowerRankingName(), flowerRanking.getFlowerRankingDescription(), flowerRanking.getFlowerRankingPrize());
        } else{
            flowerRanking.update(pumName, null, avgAmt);
            return new FlowerRankingResponseDto(flowerRanking.getFlowerRankingId(), flowerRanking.getFlowerRankingName(), flowerRanking.getFlowerRankingDescription(), flowerRanking.getFlowerRankingPrize());
        }
    }

    @Transactional
    public List<FlowerRankingResponseDto> getFlowerRanking(){
        List<FlowerRankingResponseDto> flowerRankings = new ArrayList<>();
        for (long i=0; i<3; i++){
            FlowerRanking flowerRankingOne = flowerRankingRepository.findByFlowerRankingId(i+1);
            FlowerRankingResponseDto flowerRankingResponseDtoOne = new FlowerRankingResponseDto(flowerRankingOne.getFlowerRankingId(), flowerRankingOne.getFlowerRankingName(), flowerRankingOne.getFlowerRankingDescription(), flowerRankingOne.getFlowerRankingPrize());
            flowerRankings.add(flowerRankingResponseDtoOne);
        }
        return flowerRankings;
    }

}
