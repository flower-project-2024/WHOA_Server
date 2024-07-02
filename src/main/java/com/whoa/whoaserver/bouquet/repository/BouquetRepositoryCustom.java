package com.whoa.whoaserver.bouquet.repository;

import com.whoa.whoaserver.bouquet.domain.Bouquet;

import java.util.Optional;

public interface BouquetRepositoryCustom {

    Optional<Bouquet> findByBouquetIdWithMember(Long memberId, Long bouquetId);
}
