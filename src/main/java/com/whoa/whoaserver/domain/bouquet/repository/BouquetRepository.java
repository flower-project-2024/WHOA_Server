package com.whoa.whoaserver.domain.bouquet.repository;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import com.whoa.whoaserver.domain.bouquet.domain.type.BouquetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BouquetRepository extends JpaRepository<Bouquet, Long>, BouquetRepositoryCustom {
	Optional<Bouquet> findByMemberIdAndId(Long memberId, Long bouquetId);

	List<Bouquet> findAllByMemberId(Long memberId);

	List<Bouquet> findAllByMemberIdOrderByIdDesc(Long memberId);

	Optional<Bouquet> findByMemberIdAndBouquetName(Long memberId, String bouquetName);

	List<Bouquet> findAllByMemberIdAndBouquetStatus(Long memberId, BouquetStatus bouquetStatus);

}
