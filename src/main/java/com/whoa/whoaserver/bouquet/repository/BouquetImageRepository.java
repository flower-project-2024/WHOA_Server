package com.whoa.whoaserver.bouquet.repository;

import com.whoa.whoaserver.bouquet.domain.BouquetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BouquetImageRepository extends JpaRepository<BouquetImage, Long> {

    boolean existsByFileName(String fileName);
}
