package com.whoa.whoaserver.keyword.service;

import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flower.repository.FlowerImageRepository;
import com.whoa.whoaserver.flower.utils.FlowerUtils;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.keyword.dto.response.FlowerInfoByKeywordResponse;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.mapping.repository.FlowerExpressionKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerKeywordService {
	private static final int TOTAL_FLOWER_INFORMATION = 0;

	private final FlowerExpressionKeywordRepository flowerExpressionKeywordRepository;
	private final FlowerImageRepository flowerImageRepository;

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "keyword", key = "#keywordId")
	public List<FlowerInfoByKeywordResponse> getFlowerInfoByKeyword(final Long keywordId) {
		List<FlowerExpression> flowerExpressionList;
		if (keywordId == TOTAL_FLOWER_INFORMATION) {
			flowerExpressionList = getAllFlowerExpressions();
		} else {
			flowerExpressionList = getExpressionsByKeyword(keywordId);
		}

		Set<FlowerExpression> uniqueFlowerExpressions = flowerExpressionList.stream().collect(Collectors.toUnmodifiableSet());

		return uniqueFlowerExpressions.stream()
			.map(this::mapToResponse)
			.collect(Collectors.toUnmodifiableList());
	}

	private List<FlowerExpression> getAllFlowerExpressions() {
		List<FlowerExpressionKeyword> mapping = flowerExpressionKeywordRepository.findAll();
		return mapping.stream()
			.map(FlowerExpressionKeyword::getFlowerExpression)
			.filter(this::isInContemplationPeriod)
			.collect(Collectors.toUnmodifiableList());
	}

	private List<FlowerExpression> getExpressionsByKeyword(Long keywordId) {
		List<FlowerExpressionKeyword> mapping = flowerExpressionKeywordRepository.findByKeywordIdWithExpressions(keywordId);
		return mapping.stream()
			.map(FlowerExpressionKeyword::getFlowerExpression)
			.filter(this::isInContemplationPeriod)
			.collect(Collectors.toUnmodifiableList());
	}

	public boolean isInContemplationPeriod(FlowerExpression flowerExpression) {
		return FlowerUtils.parseFlowerEnumerationColumn(flowerExpression.getFlower().getComtemplationPeriod())
			.contains(String.valueOf(LocalDate.now().getMonthValue()));
	}

	private FlowerInfoByKeywordResponse mapToResponse(FlowerExpression flowerExpression) {
		List<String> keywordNames = flowerExpression.getFlowerExpressionKeywords().stream()
			.map(flowerExpressionKeyword -> flowerExpressionKeyword.getKeyword().getKeywordName())
			.collect(Collectors.toUnmodifiableList());

		FlowerImage flowerImage = flowerImageRepository.findByFlowerExpression(flowerExpression)
			.orElse(null);

		return FlowerInfoByKeywordResponse.fromFlowerExpressionAndKeyword(flowerExpression, flowerImage, keywordNames);
	}
}
