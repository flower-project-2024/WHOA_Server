package com.whoa.whoaserver.domain.flowerExpression.repository;

import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlowerExpressionRepository extends JpaRepository<FlowerExpression, Long> {
	List<FlowerExpression> findByFlowerLanguageContaining(String flowerKeyword);

	@EntityGraph(attributePaths = {"flowerImage"})
	FlowerExpression findByFlowerExpressionId(Long flowerExpressionid);

	List<FlowerExpression> findByFlowerFlowerId(Long flowerId);

	@Query("SELECT DISTINCT f.flowerColor FROM FlowerExpression f")
	List<String> findDistinctFlowerColors();
}
