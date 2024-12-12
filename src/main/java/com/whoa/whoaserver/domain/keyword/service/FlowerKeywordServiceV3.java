package com.whoa.whoaserver.domain.keyword.service;

import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.domain.keyword.dto.response.FlowerInfoByKeywordResponseV2;
import com.whoa.whoaserver.domain.mapping.domain.CustomizingPurposeKeyword;
import com.whoa.whoaserver.domain.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.domain.mapping.repository.CustomizingPurposeKeywordRepository;
import com.whoa.whoaserver.global.exception.WhoaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.whoa.whoaserver.global.exception.ExceptionCode.INVALID_MATCHING_WITH_CUSTOMIZING_PURPOSE_AND_KEYWORD;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerKeywordServiceV3 {
	private static final int TOTAL_FLOWER_INFORMATION_FLAG_BY_KEYWORD_ID = 0;

	private final CustomizingPurposeKeywordRepository customizingPurposeKeywordRepository;
	private final FlowerKeywordService flowerKeywordService;

	@Transactional(readOnly = true)
	public List<FlowerInfoByKeywordResponseV2> getFlowerInfoByKeywordAndCustomizingPurposeAndColor(Long customizingPurposeId, Long keywordId, List<String> selectedColors) {
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
			.filter(flowerExpression -> selectedColors.contains(flowerExpression.getFlowerColor()))
			.toList();

		return targetFlowerExpressionByCustomizingPurpose.stream()
			.map(FlowerInfoByKeywordResponseV2::from)
			.toList();
	}
}
