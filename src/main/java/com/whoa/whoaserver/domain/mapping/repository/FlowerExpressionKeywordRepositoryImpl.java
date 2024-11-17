package com.whoa.whoaserver.domain.mapping.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whoa.whoaserver.domain.flowerExpression.domain.QFlowerExpression;
import com.whoa.whoaserver.domain.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.domain.mapping.domain.QFlowerExpressionKeyword;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FlowerExpressionKeywordRepositoryImpl implements FlowerExpressionKeywordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FlowerExpressionKeyword> findByKeywordIdWithExpressions(Long keywordId) {
        QFlowerExpressionKeyword flowerExpressionKeyword = QFlowerExpressionKeyword.flowerExpressionKeyword;
        QFlowerExpression flowerExpression = QFlowerExpression.flowerExpression;


        return jpaQueryFactory.selectFrom(flowerExpressionKeyword)
                .join(flowerExpressionKeyword.flowerExpression, flowerExpression).fetchJoin()
                .where(flowerExpressionKeyword.keyword.keywordId.eq(keywordId))
                .fetch();
    }
}
