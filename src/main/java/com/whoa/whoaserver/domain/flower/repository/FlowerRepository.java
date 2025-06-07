package com.whoa.whoaserver.domain.flower.repository;

import com.whoa.whoaserver.domain.flower.domain.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlowerRepository extends JpaRepository<Flower, Long>, FlowerRepositoryCustom {

    Flower findByFlowerId(final Long flowerId);

    Flower findByFlowerName(String flowerName);

    Optional<Flower> findFlowerByRecommendDate(String recommendDate);

}
