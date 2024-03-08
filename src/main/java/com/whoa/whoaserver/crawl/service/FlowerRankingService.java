package com.whoa.whoaserver.crawl.service;


import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import com.whoa.whoaserver.crawl.dto.FlowerRankingResponseDto;
import com.whoa.whoaserver.crawl.repository.FlowerRankingRepository;
import com.whoa.whoaserver.flower.dto.FlowerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlowerRankingService {

    final FlowerRankingRepository flowerRankingRepository;

    @Transactional
    public FlowerRankingResponseDto saveFlowerRanking(final Long flowerRankingId, final String pumName, final String avgAmt) {
        FlowerRanking flowerRanking = flowerRankingRepository.findByFlowerRankingId(flowerRankingId);
        flowerRanking.update(flowerRanking.getFlowerRankingName(), flowerRanking.getFlowerRankingLanguage(), flowerRanking.getFlowerRankingPrize());
        return new FlowerRankingResponseDto(flowerRanking.getFlowerRankingId(), flowerRanking.getFlowerRankingName(), flowerRanking.getFlowerRankingLanguage(), flowerRanking.getFlowerRankingPrize());
    }

}
