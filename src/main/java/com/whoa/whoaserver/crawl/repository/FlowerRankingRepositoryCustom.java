package com.whoa.whoaserver.crawl.repository;

import com.whoa.whoaserver.crawl.dto.FlowerRankingResponseDto;

import java.util.List;

public interface FlowerRankingRepositoryCustom {
	List<FlowerRankingResponseDto> findAllFlowerRankingInformation();
}
