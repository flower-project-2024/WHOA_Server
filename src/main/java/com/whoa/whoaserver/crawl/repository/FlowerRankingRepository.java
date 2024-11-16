package com.whoa.whoaserver.crawl.repository;

import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRankingRepository extends JpaRepository<FlowerRanking, Long>, FlowerRankingRepositoryCustom {
    FlowerRanking findByFlowerRankingId(final Long flowerRankingId);

}
