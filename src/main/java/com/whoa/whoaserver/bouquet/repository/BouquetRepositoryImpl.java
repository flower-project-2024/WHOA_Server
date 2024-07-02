package com.whoa.whoaserver.bouquet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.domain.QBouquet;
import com.whoa.whoaserver.member.domain.QMember;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
