package com.whoa.whoaserver.bouquet.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BouquetRepository extends JpaRepository<Bouquet, Long>{
    Optional<Bouquet> findByMemberIdAndId(Long memberId, Long bouquetId);

    Optional<List<Bouquet>> findByMemberId(Long memberId);
}
