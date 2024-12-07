package com.whoa.whoaserver.domain.flower.repository.ranking;

import com.whoa.whoaserver.domain.flower.domain.FlowerRanking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlowerRankingRepository extends JpaRepository<FlowerRanking, Long>, FlowerRankingRepositoryCustom {
	FlowerRanking findByFlowerRankingId(final Long flowerRankingId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE flower_ranking SET flower_ranking_date = :formattedDate", nativeQuery = true)
	void updateAllFlowerRankingDates(@Param("formattedDate") String formattedDate);
}
