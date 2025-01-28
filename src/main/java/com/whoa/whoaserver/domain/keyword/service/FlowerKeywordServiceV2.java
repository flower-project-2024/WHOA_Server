package com.whoa.whoaserver.domain.keyword.service;

import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.domain.keyword.dto.response.FlowerInfoByKeywordResponseV2;
import com.whoa.whoaserver.domain.mapping.domain.CustomizingPurposeKeyword;
import com.whoa.whoaserver.domain.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.domain.mapping.repository.CustomizingPurposeKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.INVALID_MATCHING_WITH_CUSTOMIZING_PURPOSE_AND_KEYWORD;
import static com.whoa.whoaserver.global.utils.ClientUtils.getClientIP;

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
		List<CustomizingPurposeKeyword> customizingPurposeKeywordList = getCustomizingPurposeKeywordListByTotalKeywordFlag(customizingPurposeId, keywordId);

		List<FlowerExpression> targetFlowerExpressionByCustomizingPurpose = getFlowerExpressionByCustomizingPurposeKeyword(customizingPurposeKeywordList);

		return mappingFlowerExpressionByCustomizingPurposeToFlowerInfoByKeywordResponse(targetFlowerExpressionByCustomizingPurpose);
	}

	public List<CustomizingPurposeKeyword> getCustomizingPurposeKeywordListByTotalKeywordFlag(Long customizingPurposeId, Long keywordId) {
		String clientIP = getClientIP();
		List<CustomizingPurposeKeyword> customizingPurposeKeywordList;
		if (keywordId == TOTAL_FLOWER_INFORMATION_FLAG_BY_KEYWORD_ID) {
			customizingPurposeKeywordList = customizingPurposeKeywordRepository.findAllByCustomizingPurpose_CustomizingPurposeId(customizingPurposeId);
		} else {
			customizingPurposeKeywordList = customizingPurposeKeywordRepository.findAllByCustomizingPurpose_CustomizingPurposeIdAndKeyword_KeywordId(customizingPurposeId, keywordId);

			if (customizingPurposeKeywordList.isEmpty()) {
				throw new WhoaException(
					INVALID_MATCHING_WITH_CUSTOMIZING_PURPOSE_AND_KEYWORD,
					"getCustomizingPurposeKeywordListByTotalKeywordFlag - controller에서 넘겨 받은 구매목적과 키워드 간 매칭되는 CustomizingPurposeKeyword를 찾을 수 없음",
					clientIP,
					"customizingPurposeId request : " + customizingPurposeId + ", keywordId request : " + keywordId
				);
			}
		}

		return customizingPurposeKeywordList;
	}

	public List<FlowerExpression> getFlowerExpressionByCustomizingPurposeKeyword(List<CustomizingPurposeKeyword> customizingPurposeKeywordList) {
		return customizingPurposeKeywordList.stream()
			.map(CustomizingPurposeKeyword::getKeyword)
			.flatMap(keyword -> keyword.getFlowerExpressionKeywords().stream())
			.map(FlowerExpressionKeyword::getFlowerExpression)
			.filter(flowerKeywordService::isInContemplationPeriod)
			.collect(Collectors.toUnmodifiableList());
	}

	public List<FlowerInfoByKeywordResponseV2> mappingFlowerExpressionByCustomizingPurposeToFlowerInfoByKeywordResponse(List<FlowerExpression> targetFlowerExpressionByCustomizingPurpose) {
		return targetFlowerExpressionByCustomizingPurpose.stream()
			.map(FlowerInfoByKeywordResponseV2::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
