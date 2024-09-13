package com.whoa.whoaserver.keyword.service;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.keyword.dto.response.FlowerInfoByKeywordResponseV2;
import com.whoa.whoaserver.mapping.domain.CustomizingPurposeKeyword;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.mapping.repository.CustomizingPurposeKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerKeywordServiceV2 {

	private final CustomizingPurposeKeywordRepository customizingPurposeKeywordRepository;

	public List<FlowerInfoByKeywordResponseV2> getFlowerInfoByKeywordAndCustomizingPurpose(Long customizingPurposeId, Long keywordId) {

		List<CustomizingPurposeKeyword> targetCustomizingPurposeKeywords = customizingPurposeKeywordRepository.findAllByCustomizingPurpose_CustomizingPurposeIdAndKeyword_KeywordId(customizingPurposeId, keywordId);

		List<FlowerExpression> targetFlowerExpressionByCustomizingPurpose = targetCustomizingPurposeKeywords.stream()
			.map(CustomizingPurposeKeyword::getKeyword)
			.flatMap(keyword -> keyword.getFlowerExpressionKeywords().stream())
			.map(FlowerExpressionKeyword::getFlowerExpression)
			.collect(Collectors.toUnmodifiableList());

		return targetFlowerExpressionByCustomizingPurpose.stream()
			.map(FlowerInfoByKeywordResponseV2::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
