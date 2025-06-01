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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.utils.LoggerUtils.logger;
import static com.whoa.whoaserver.global.utils.ClientUtils.getClientIP;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerKeywordServiceV3 {

	private final FlowerExpressionRepository flowerExpressionRepository;
	private final FlowerKeywordServiceV2 flowerKeywordServiceV2;

	@Transactional(readOnly = true)
	public List<FlowerInfoByKeywordResponseV2> getFlowerInfoByKeywordAndCustomizingPurposeAndColor(Long customizingPurposeId, Long keywordId, List<String> selectedColors) {
		String clientIP = getClientIP();
		if (selectedColors == null || selectedColors.isEmpty()) {
			return flowerKeywordServiceV2.getFlowerInfoByKeywordAndCustomizingPurpose(customizingPurposeId, keywordId);
		} else {
			Boolean isContained = hasCaseInsensitiveColorMatch(selectedColors);
			if (!isContained) throw new WhoaException(
				ExceptionCode.INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWEREXPRESSION_FLOWER_COLORS,
				"getFlowerInfoByKeywordAndCustomizingPurposeAndColor - requestParam으로 넘긴 원하는 색상들이 DB flowerExpression 색깔 계열 범주에 포함되는 색이 단 한 건도 없음",
				clientIP,
				"selectedColors list value : " + selectedColors.stream().collect(Collectors.joining(", "))
			);

			List<CustomizingPurposeKeyword> customizingPurposeKeywordList = flowerKeywordServiceV2.getCustomizingPurposeKeywordListByTotalKeywordFlag(customizingPurposeId, keywordId);

			List<String> normalizeColorLists = prepareCaseInsensitiveBouquetColorLists(selectedColors);

			List<FlowerExpression> targetFlowerExpressionByCustomizingPurpose = flowerKeywordServiceV2.getFlowerExpressionByCustomizingPurposeKeyword(customizingPurposeKeywordList)
				.stream()
				.filter(flowerExpression -> isValidColorMatch(flowerExpression, normalizeColorLists))
				.toList();

			if (targetFlowerExpressionByCustomizingPurpose.isEmpty()) {
				throw new WhoaException(
					ExceptionCode.INVALID_MATCHING_WITH_BOUQUET_SELECTED_COLORS_AND_FLOWER_COLORS_AND_KEYWORD_AND_CUSTOMIZING_PURPOSE,
					"getFlowerInfoByKeywordAndCustomizingPurposeAndColor - " + "\n"
						+ "\t1. requestParam selectedColors가 db 전체를 고려했을 때 유사 색감 대상에 해당 되고"
						+ "\t2. 넘겨받은 구매목적과 키워드가 매칭되는 CustomizingPurposeKeyword도 있지만"
						+ "\tCustomizingPurposeKeyword -> Keyword -> FlowerExpressionKeyword -> FlowerExpression의 색이 selectedColorFamily에 해당되는 경우가 없음",
					clientIP,
					null
				);
			}

			return flowerKeywordServiceV2.mappingFlowerExpressionByCustomizingPurposeToFlowerInfoByKeywordResponse(targetFlowerExpressionByCustomizingPurpose);
		}
	}

	private Boolean hasCaseInsensitiveColorMatch(List<String> selectedColors) {
		List<String> flowerColors = prepareTotalCaseInsensitiveFlowerExpressionColorList();
		logger.info("db flowerExpression flowerColor values : {}", flowerColors);

		List<String> selectedColorsForCaseInsensitiveComparsion = prepareCaseInsensitiveBouquetColorLists(selectedColors);
		logger.info("front selectedColors에서 각 색상이 속한 모든 색감군에 포함되는 색상 종류 전체 : {}", selectedColorsForCaseInsensitiveComparsion);

		boolean isContained = false;
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
			.map(String::toUpperCase)
			.toList();
	}

	private List<String> prepareCaseInsensitiveBouquetColorLists(List<String> selectedColors) {
		ArrayList<String> allFamilyPalleteColors = getAllColorFamilyPaletteFromSelectedColors(selectedColors);
		return allFamilyPalleteColors.stream()
			.map(String::toUpperCase)
			.toList();
	}

	private Boolean isValidColorMatch(FlowerExpression flowerExpression, List<String> normalizeColorLists) {
		ArrayList<String> allFamilyPalleteColors = getAllColorFamilyPaletteFromSelectedColors(normalizeColorLists);
		return allFamilyPalleteColors.contains(flowerExpression.getFlowerColor().toUpperCase());
	}

	private ArrayList<String> getAllColorFamilyPaletteFromSelectedColors(List<String> selectedColors) {
		Map<String, String> paletteColorMap = PaletteColors.getPaletteColorMap();
		Map<String, List<String>> colorPaletteMap = PaletteColors.getColorPaletteMap();

		HashSet<String> allPalatte = new HashSet<>();

		for (String selectedColor : selectedColors) {
			allPalatte.addAll(colorPaletteMap.get(paletteColorMap.get(selectedColor.toUpperCase())));
		}

		return new ArrayList<>(allPalatte);
	}
}
