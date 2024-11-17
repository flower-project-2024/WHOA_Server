package com.whoa.whoaserver.domain.flower.repository.image;

import com.whoa.whoaserver.domain.flower.domain.FlowerImage;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlowerImageRepository extends JpaRepository<FlowerImage, Long> {

    Optional<FlowerImage> findByFlowerExpression(FlowerExpression flowerExpression);
}
