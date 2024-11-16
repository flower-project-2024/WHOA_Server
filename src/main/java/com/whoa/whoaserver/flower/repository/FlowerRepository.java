package com.whoa.whoaserver.flower.repository;

import com.whoa.whoaserver.flower.domain.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FlowerRepository extends JpaRepository<Flower, Long>, FlowerRepositoryCustom {

    Flower findByFlowerId(final Long flowerId);

    Flower findByFlowerName(String flowerName);

    Optional<Flower> findFlowerByRecommendDate(String recommendDate);

    @Query("SELECT f FROM Flower f ORDER BY RAND() LIMIT 1")
    Flower findRandomFlower();

    @Query("SELECT f.flowerDescription FROM Flower f WHERE f.flowerName = :flowerName")
    Optional<String> findFlowerDescriptionByFlowerName(@Param("flowerName") String flowerName);

    @Query("SELECT f FROM Flower f JOIN FETCH f.flowerExpressions fe WHERE f.flowerId = :flowerId")
    Optional<Flower> findFlowerByIdWithExpressions(@Param("flowerId") Long flowerId);

}
