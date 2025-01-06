package com.whoa.whoaserver.domain.flower.repository.ranking;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerPopularityResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whoa.whoaserver.domain.flower.domain.QFlowerPopularity.flowerPopularity;

@RequiredArgsConstructor
public class FlowerPopularityRepositoryImpl implements FlowerPopularityRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FlowerPopularityResponseDto> findAllFlowerPopularityRanking() {
		return jpaQueryFactory
			.select(Projections.constructor(FlowerPopularityResponseDto.class,
				flowerPopularity.flowerId,
				flowerPopularity.flowerImageUrl,
				flowerPopularity.flowerRanking,
				flowerPopularity.flowerName,
				flowerPopularity.flowerLanguage
				))
			.from(flowerPopularity)
			.fetch();
	}
}
