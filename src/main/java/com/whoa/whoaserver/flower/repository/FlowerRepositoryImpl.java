package com.whoa.whoaserver.flower.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whoa.whoaserver.flower.dto.FlowerSearchResponseDto;
import lombok.RequiredArgsConstructor;

import static com.whoa.whoaserver.flower.domain.QFlower.flower;

@RequiredArgsConstructor
public class FlowerRepositoryImpl implements FlowerRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public FlowerSearchResponseDto findAllFlowers(Long flowerId) {
		return jpaQueryFactory
			.select(Projections.constructor(FlowerSearchResponseDto.class,
				flower.flowerId,
				flower.flowerName))
			.from(flower)
			.where(flower.flowerId.eq(flowerId))
			.fetchOne();
	}
}
