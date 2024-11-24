package com.whoa.whoaserver.domain.flower.repository;

import com.querydsl.core.types.Projections;
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
		Long maxId = jpaQueryFactory
			.select(flower.flowerId.max())
			.from(flower)
			.fetchOne();

		if (maxId == null || maxId == 0) {
			return null;
		}

		Long randomId = (long) (Math.random() * maxId) + 1;

		return jpaQueryFactory
			.selectFrom(flower)
			.from(flower)
			.where(flower.flowerId.goe(randomId))
			.limit(1)
			.fetchOne();
	}
}
