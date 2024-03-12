package com.whoa.whoaserver.crawl.repository;

import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlowerRankingRepository extends JpaRepository<FlowerRanking, Long> {
    FlowerRanking findByFlowerRankingId(final Long flowerRankingId);

    Optional<String> findFlowerDescriptionByFlowerRankingName(final String flowerName);
}
