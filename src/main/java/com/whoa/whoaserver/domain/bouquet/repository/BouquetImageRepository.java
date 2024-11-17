package com.whoa.whoaserver.domain.bouquet.repository;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import com.whoa.whoaserver.domain.bouquet.domain.BouquetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BouquetImageRepository extends JpaRepository<BouquetImage, Long> {

    boolean existsByFileName(String fileName);

	List<BouquetImage> findAllByBouquet(Bouquet bouquet);
}
