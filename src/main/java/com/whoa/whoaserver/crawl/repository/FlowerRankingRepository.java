package com.whoa.whoaserver.crawl.repository;

import com.whoa.whoaserver.crawl.FlowerCrawlerScheduler;
import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRankingRepository extends JpaRepository<FlowerRanking, Long> {
    FlowerRanking findByFlowerRankingId(final Long flowerRankingId);
}
