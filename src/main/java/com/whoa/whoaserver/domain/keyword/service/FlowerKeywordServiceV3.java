package com.whoa.whoaserver.domain.keyword.service;

import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.domain.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.domain.keyword.dto.response.FlowerInfoByKeywordResponseV2;
import com.whoa.whoaserver.domain.mapping.domain.CustomizingPurposeKeyword;
import com.whoa.whoaserver.domain.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.domain.mapping.repository.CustomizingPurposeKeywordRepository;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.INVALID_MATCHING_WITH_CUSTOMIZING_PURPOSE_AND_KEYWORD;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerKeywordServiceV3 {
	private static final int TOTAL_FLOWER_INFORMATION_FLAG_BY_KEYWORD_ID = 0;

	private final CustomizingPurposeKeywordRepository customizingPurposeKeywordRepository;
	private final FlowerExpressionRepository flowerExpressionRepository;
	private final FlowerKeywordService flowerKeywordService;
	private final FlowerKeywordServiceV2 flowerKeywordServiceV2;

	@Transactional(readOnly = true)
	public List<FlowerInfoByKeywordResponseV2> getFlowerInfoByKeywordAndCustomizingPurposeAndColor(Long customizingPurposeId, Long keywordId, List<String> selectedColors) {
		if (selectedColors == null || selectedColors.isEmpty()) {
			return flowerKeywordServiceV2.getFlowerInfoByKeywordAndCustomizingPurpose(customizingPurposeId, keywordId);
		} else {
			List<String> flowerColors = flowerExpressionRepository.findDistinctFlowerColors().stream()
				.map(String::toLowerCase)
				.toList();

			List<String> selectedColorsForCaseInsensitiveComparsion = selectedColors.stream()
				.map(String::toLowerCase)
				.toList();

			Boolean isContained = false;
			for (String selectBouquetColor : selectedColorsForCaseInsensitiveComparsion) {
				if (flowerColors.contains(selectBouquetColor)) {
					isContained = true;
					break;
				}
			}

			if (!isContained) throw new WhoaException(ExceptionCode.INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWEREXPRESSION_FLOWER_COLORS);

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
				.filter(flowerExpression -> selectedColorsForCaseInsensitiveComparsion.contains(flowerExpression.getFlowerColor().toLowerCase()))
				.toList();

			if (targetFlowerExpressionByCustomizingPurpose.isEmpty()) {
				throw new WhoaException(ExceptionCode.INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWER_COLORS_AND_KEYWORD_AND_CUSTOMIZING_PURPOSE);
			}

			return targetFlowerExpressionByCustomizingPurpose.stream()
				.map(FlowerInfoByKeywordResponseV2::from)
				.toList();
		}
	}
}
