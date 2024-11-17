package com.whoa.whoaserver.domain.bouquet.repository;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;

import java.util.Optional;

public interface BouquetRepositoryCustom {

    Optional<Bouquet> findByBouquetIdWithMember(Long memberId, Long bouquetId);
}
