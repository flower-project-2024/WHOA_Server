package com.whoa.whoaserver.domain.flower.repository.ranking;

import com.whoa.whoaserver.domain.flower.domain.FlowerRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRankingRepository extends JpaRepository<FlowerRanking, Long>, FlowerRankingRepositoryCustom {
    FlowerRanking findByFlowerRankingId(final Long flowerRankingId);

}
