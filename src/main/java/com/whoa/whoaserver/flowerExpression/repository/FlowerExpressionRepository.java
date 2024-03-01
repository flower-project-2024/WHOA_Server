package com.whoa.whoaserver.flowerExpression.repository;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerExpressionRepository extends JpaRepository<FlowerExpression, Long> {
    List<FlowerExpression> findByFlowerLanguageContaining(String flowerKeyword);
}
