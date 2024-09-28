package com.whoa.whoaserver.keyword.service;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.keyword.dto.response.FlowerInfoByKeywordResponseV2;
import com.whoa.whoaserver.mapping.domain.CustomizingPurposeKeyword;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.mapping.repository.CustomizingPurposeKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.INVALID_MATCHING_WITH_CUSTOMIZING_PURPOSE_AND_KEYWORD;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerKeywordServiceV2 {
	private static final int TOTAL_FLOWER_INFORMATION_FLAG_BY_KEYWORD_ID = 0;

	private final CustomizingPurposeKeywordRepository customizingPurposeKeywordRepository;
	private final FlowerKeywordService flowerKeywordService;

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "CustomizingPurposeAndKeyword", key = "{#customizingPurposeId, #keywordId}")
	public List<FlowerInfoByKeywordResponseV2> getFlowerInfoByKeywordAndCustomizingPurpose(Long customizingPurposeId, Long keywordId) {
		List<CustomizingPurposeKeyword> customizingPurposeKeywordList;

		if (keywordId == TOTAL_FLOWER_INFORMATION_FLAG_BY_KEYWORD_ID) {
			customizingPurposeKeywordList = customizingPurposeKeywordRepository.findAllByCustomizingPurpose_CustomizingPurposeId(customizingPurposeId);
		} else {
			customizingPurposeKeywordList = customizingPurposeKeywordRepository.findAllByCustomizingPurpose_CustomizingPurposeIdAndKeyword_KeywordId(customizingPurposeId, keywordId);

			if (customizingPurposeKeywordList.isEmpty()) {
				throw new WhoaException(INVALID_MATCHING_WITH_CUSTOMIZING_PURPOSE_AND_KEYWORD);
			}
		}

		List<FlowerExpression> targetFlowerExpressionByCustomizingPurpose = customizingPurposeKeywordList.stream()
			.map(CustomizingPurposeKeyword::getKeyword)
			.flatMap(keyword -> keyword.getFlowerExpressionKeywords().stream())
			.map(FlowerExpressionKeyword::getFlowerExpression)
			.filter(flowerKeywordService::isInContemplationPeriod)
			.collect(Collectors.toUnmodifiableList());

		return targetFlowerExpressionByCustomizingPurpose.stream()
			.map(FlowerInfoByKeywordResponseV2::from)
			.collect(Collectors.toUnmodifiableList());


	}
}
