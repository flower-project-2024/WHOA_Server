package com.whoa.whoaserver.keyword.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.keyword.domain.Keyword;
import com.whoa.whoaserver.keyword.dto.response.FlowerInfoByKeywordResponse;
import com.whoa.whoaserver.keyword.repository.FlowerKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlowerKeywordService {

    private final FlowerKeywordRepository flowerKeywordRepository;
    private final FlowerExpressionRepository flowerExpressionRepository;

    @Transactional
    public List<FlowerInfoByKeywordResponse> getFlowerInfoByKeyword(final Long keywordId) {
        Keyword targetKeyword = flowerKeywordRepository.findByKeywordId(keywordId);
        List<FlowerExpression> expressionCandidates = flowerExpressionRepository.findByFlowerLanguageContaining(targetKeyword.getKeywordName());

        List<FlowerInfoByKeywordResponse> flowerInfoResponse = expressionCandidates.stream()
                .map(flowerExpression -> {
                    Flower flower = flowerExpression.getFlower();
                    return new FlowerInfoByKeywordResponse(
                            flower.getFlowerName(),
                            flower.getFlowerImage(),
                            List.of(flowerExpression.getFlowerLanguage())
                    );
                })
                .collect(Collectors.toList());

        return flowerInfoResponse;

    }
}
