package com.whoa.whoaserver.crawl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whoa.whoaserver.crawl.dto.FlowerRankingResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whoa.whoaserver.crawl.domain.QFlowerRanking.flowerRanking;

@RequiredArgsConstructor
public class FlowerRankingRepositoryImpl implements FlowerRankingRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FlowerRankingResponseDto> findAllFlowerRankingInformation() {
		return jpaQueryFactory
			.select(Projections.constructor(FlowerRankingResponseDto.class,
				flowerRanking.flowerRankingId,
				flowerRanking.flowerRankingName,
				flowerRanking.flowerRankingLanguage,
				flowerRanking.flowerRankingPrice,
				flowerRanking.flowerRankingDate,
				flowerRanking.flowerImage,
				flowerRanking.flowerId
				))
			.from(flowerRanking)
			.fetch();
	}
}
