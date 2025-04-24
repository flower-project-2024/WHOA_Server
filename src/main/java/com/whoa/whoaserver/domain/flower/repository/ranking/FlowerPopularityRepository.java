package com.whoa.whoaserver.domain.flower.repository.ranking;

import com.whoa.whoaserver.domain.flower.domain.FlowerPopularity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlowerPopularityRepository extends JpaRepository<FlowerPopularity, Long>, FlowerPopularityRepositoryCustom {
	Optional<FlowerPopularity> findByFlowerId(Long flowerId);
}
