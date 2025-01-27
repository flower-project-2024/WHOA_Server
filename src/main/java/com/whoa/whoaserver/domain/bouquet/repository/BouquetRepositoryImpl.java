package com.whoa.whoaserver.domain.bouquet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import com.whoa.whoaserver.domain.bouquet.domain.QBouquet;
import com.whoa.whoaserver.domain.member.domain.QMember;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BouquetRepositoryImpl implements BouquetRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Bouquet> findByBouquetIdWithMember(Long memberId, Long bouquetId) {
        QBouquet bouquet = QBouquet.bouquet;
        QMember member = QMember.member;

        Bouquet result = jpaQueryFactory.selectFrom(bouquet)
                            .join(bouquet.member, member).fetchJoin()
                            .where(bouquet.member.id.eq(memberId)
                                .and(bouquet.id.eq(bouquetId)))
                            .fetchOne();

        return Optional.ofNullable(result);
    }

	@Override
	public List<String> findAllFlowerTypeInformation() {
		QBouquet bouquet = QBouquet.bouquet;

		return jpaQueryFactory.select(bouquet.flowerType).from(bouquet).fetch();
	}
}
