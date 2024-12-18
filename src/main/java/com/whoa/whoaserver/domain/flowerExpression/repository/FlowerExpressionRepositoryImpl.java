package com.whoa.whoaserver.domain.flowerExpression.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.whoa.whoaserver.domain.flowerExpression.domain.QFlowerExpression.flowerExpression;

@Repository
@AllArgsConstructor
public class FlowerExpressionRepositoryImpl implements FlowerExpressionRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<String> findDistinctFlowerColors() {
		return jpaQueryFactory
			.select(flowerExpression.flowerColor)
			.distinct()
			.from(flowerExpression)
			.fetch();
	}
}
