package com.whoa.whoaserver.flower.repository;

import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlowerImageRepository extends JpaRepository<FlowerImage, Long> {

    Optional<FlowerImage> findByFlowerExpression(FlowerExpression flowerExpression);
}
