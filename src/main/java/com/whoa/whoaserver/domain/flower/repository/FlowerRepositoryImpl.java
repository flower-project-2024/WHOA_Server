package com.whoa.whoaserver.domain.flower.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
