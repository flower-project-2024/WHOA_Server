package com.whoa.whoaserver.flower.repository;

import com.whoa.whoaserver.flower.domain.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRepository extends JpaRepository<Flower, Long> {

    Flower findByFlowerId(final Long flowerId);
    
    Flower findFlowerByRecommendDate(String recommendDate);
}
