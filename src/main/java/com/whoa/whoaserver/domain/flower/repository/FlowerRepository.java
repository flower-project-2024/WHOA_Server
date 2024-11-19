package com.whoa.whoaserver.domain.flower.repository;

import com.whoa.whoaserver.domain.flower.domain.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FlowerRepository extends JpaRepository<Flower, Long>, FlowerRepositoryCustom {

    Flower findByFlowerId(final Long flowerId);

    Flower findByFlowerName(String flowerName);

    Optional<Flower> findFlowerByRecommendDate(String recommendDate);

    @Query(value = "SELECT * FROM flower WHERE flower_id >= (SELECT FLOOR(RAND() * (SELECT MAX(flower_id) FROM flower)) + 1) LIMIT 1", nativeQuery = true)
    Flower findRandomFlower();

    @Query("SELECT f.flowerDescription FROM Flower f WHERE f.flowerName = :flowerName")
    Optional<String> findFlowerDescriptionByFlowerName(@Param("flowerName") String flowerName);

    @Query("SELECT f FROM Flower f JOIN FETCH f.flowerExpressions fe WHERE f.flowerId = :flowerId")
    Optional<Flower> findFlowerByIdWithExpressions(@Param("flowerId") Long flowerId);

}
