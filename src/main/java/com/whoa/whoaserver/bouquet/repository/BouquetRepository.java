package com.whoa.whoaserver.bouquet.repository;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BouquetRepository extends JpaRepository<Bouquet, Long>, BouquetRepositoryCustom {
    Optional<Bouquet> findByMemberIdAndId(Long memberId, Long bouquetId);

    Optional<List<Bouquet>> findByMemberId(Long memberId);

    Optional<Bouquet> findByMemberIdAndBouquetName(Long memberId, String bouquetName);

}
