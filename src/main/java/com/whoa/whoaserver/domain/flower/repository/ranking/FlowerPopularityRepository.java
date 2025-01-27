package com.whoa.whoaserver.domain.flower.repository.ranking;

import com.whoa.whoaserver.domain.flower.domain.FlowerPopularity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerPopularityRepository extends JpaRepository<FlowerPopularity, Long>, FlowerPopularityRepositoryCustom {
	FlowerPopularity findByFlowerId(Long flowerId);
}
