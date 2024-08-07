package com.whoa.whoaserver.crawl.service;


import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import com.whoa.whoaserver.crawl.dto.FlowerRankingResponseDto;
import com.whoa.whoaserver.crawl.repository.FlowerRankingRepository;
import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.domain.FlowerImage;
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
        Flower findFlower = flowerRepository.findByFlowerName(pumName);
        if (findFlower == null){
            flowerRanking.update(pumName, null, avgAmt, date, null, null);
        }
        else{
            String findFlowerLanguage = findFlower.getFlowerExpressions().get(0).getFlowerLanguage();
            String flowerImageUrl = null;
            for (FlowerImage flowerImage : findFlower.getFlowerImages()) {
                flowerImageUrl = flowerImage.getImageUrl();
                if (flowerImageUrl != null)
                    break;
            }
            Long findFlowerId = findFlower.getFlowerId();
            flowerRanking.update(pumName, findFlowerLanguage, avgAmt, date, flowerImageUrl, findFlowerId);
        }
        return new FlowerRankingResponseDto(flowerRanking.getFlowerRankingId(), flowerRanking.getFlowerRankingName(), flowerRanking.getFlowerRankingLanguage(), flowerRanking.getFlowerRankingPrice(), flowerRanking.getFlowerRankingDate(), flowerRanking.getFlowerImage(), flowerRanking.getFlowerId());
    }

    @Transactional
    public List<FlowerRankingResponseDto> getFlowerRanking(){
        List<FlowerRankingResponseDto> flowerRankings = new ArrayList<>();
        for (long i=0; i<5; i++){
            FlowerRanking flowerRankingOne = flowerRankingRepository.findByFlowerRankingId(i+1);
            FlowerRankingResponseDto flowerRankingResponseDtoOne = new FlowerRankingResponseDto(flowerRankingOne.getFlowerRankingId(), flowerRankingOne.getFlowerRankingName(), flowerRankingOne.getFlowerRankingLanguage(), flowerRankingOne.getFlowerRankingPrice(), flowerRankingOne.getFlowerRankingDate(), flowerRankingOne.getFlowerImage(), flowerRankingOne.getFlowerId());
            flowerRankings.add(flowerRankingResponseDtoOne);
        }
        return flowerRankings;
    }

}
