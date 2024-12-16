package com.whoa.whoaserver.domain.keyword.service;

import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.domain.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.domain.keyword.dto.response.FlowerInfoByKeywordResponseV2;
import com.whoa.whoaserver.domain.mapping.domain.CustomizingPurposeKeyword;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerKeywordServiceV3 {

	private final FlowerExpressionRepository flowerExpressionRepository;
	private final FlowerKeywordServiceV2 flowerKeywordServiceV2;

	@Transactional(readOnly = true)
	public List<FlowerInfoByKeywordResponseV2> getFlowerInfoByKeywordAndCustomizingPurposeAndColor(Long customizingPurposeId, Long keywordId, List<String> selectedColors) {
		if (selectedColors == null || selectedColors.isEmpty()) {
			return flowerKeywordServiceV2.getFlowerInfoByKeywordAndCustomizingPurpose(customizingPurposeId, keywordId);
		} else {
			Boolean isContained = hasCaseInsensitiveColorMatch(selectedColors);
			if (!isContained) throw new WhoaException(ExceptionCode.INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWEREXPRESSION_FLOWER_COLORS);

			List<CustomizingPurposeKeyword> customizingPurposeKeywordList = flowerKeywordServiceV2.getCustomizingPurposeKeywordListByTotalKeywordFlag(customizingPurposeId, keywordId);

			List<String> normalizeColorLists = prepareCaseInsensitiveBouquetColorLists(selectedColors);

			List<FlowerExpression> targetFlowerExpressionByCustomizingPurpose = flowerKeywordServiceV2.getFlowerExpressionByCustomizingPurposeKeyword(customizingPurposeKeywordList)
				.stream()
				.filter(flowerExpression -> isValidColorMatch(flowerExpression, normalizeColorLists))
				.toList();

			if (targetFlowerExpressionByCustomizingPurpose.isEmpty()) {
				throw new WhoaException(ExceptionCode.INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWER_COLORS_AND_KEYWORD_AND_CUSTOMIZING_PURPOSE);
			}

			return flowerKeywordServiceV2.mappingFlowerExpressionByCustomizingPurposeToFlowerInfoByKeywordResponse(targetFlowerExpressionByCustomizingPurpose);
		}
	}

	private Boolean hasCaseInsensitiveColorMatch(List<String> selectedColors) {
		List<String> flowerColors = prepareTotalCaseInsensitiveFlowerExpressionColorList();

		List<String> selectedColorsForCaseInsensitiveComparsion = prepareCaseInsensitiveBouquetColorLists(selectedColors);

		Boolean isContained = false;
		for (String selectBouquetColor : selectedColorsForCaseInsensitiveComparsion) {
			if (flowerColors.contains(selectBouquetColor)) {
				isContained = true;
				break;
			}
		}

		return isContained;
	}

	private List<String> prepareTotalCaseInsensitiveFlowerExpressionColorList() {
		 return flowerExpressionRepository.findDistinctFlowerColors().stream()
			.map(String::toLowerCase)
			.toList();
	}

	private List<String> prepareCaseInsensitiveBouquetColorLists(List<String> selectedColors) {
		return selectedColors.stream()
			.map(String::toLowerCase)
			.toList();
	}

	private Boolean isValidColorMatch(FlowerExpression flowerExpression, List<String> normalizeColorLists) {
		return normalizeColorLists.contains(flowerExpression.getFlowerColor().toLowerCase());
	}
}
