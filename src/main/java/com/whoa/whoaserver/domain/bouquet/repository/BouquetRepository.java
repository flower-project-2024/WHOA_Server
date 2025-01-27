package com.whoa.whoaserver.domain.bouquet.repository;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BouquetRepository extends JpaRepository<Bouquet, Long>, BouquetRepositoryCustom {
	Optional<Bouquet> findByMemberIdAndId(Long memberId, Long bouquetId);

	List<Bouquet> findAllByMemberId(Long memberId);

	List<Bouquet> findAllByMemberIdOrderByIdDesc(Long memberId);

	Optional<Bouquet> findByMemberIdAndBouquetName(Long memberId, String bouquetName);
}
