package com.whoa.whoaserver.domain.flower.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerSearchResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whoa.whoaserver.domain.flower.domain.QFlower.flower;

@RequiredArgsConstructor
public class FlowerRepositoryImpl implements FlowerRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FlowerSearchResponseDto> findAllFlowers() {
		return jpaQueryFactory
			.select(Projections.constructor(FlowerSearchResponseDto.class,
				flower.flowerId,
				flower.flowerName))
			.from(flower)
			.fetch();
	}

	@Override
	public Flower findRandomFlower() {
		NumberExpression<Double> randomExpression = Expressions.numberTemplate(Double.class, "RAND()");

		return jpaQueryFactory
			.selectFrom(flower)
			.where(flower.flowerId.goe(
				JPAExpressions
					.select(flower.flowerId.min()
						.add(
							JPAExpressions
								.select(flower.flowerId.max().subtract(flower.flowerId.min())
									.multiply(randomExpression))
								.from(flower)
						))
					.from(flower)
			))
			.orderBy(flower.flowerId.asc())
			.limit(1)
			.fetchOne();
	}
}
