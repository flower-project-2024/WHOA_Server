package com.whoa.whoaserver.keyword.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.keyword.domain.Keyword;
import com.whoa.whoaserver.keyword.dto.response.FlowerInfoByKeywordResponse;
import com.whoa.whoaserver.keyword.repository.FlowerKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.whoa.whoaserver.global.exception.ExceptionCode.INVALID_FLOWER_AND_EXPRESSION;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlowerKeywordService {
    private static final int TOTAL_FLOWER_INFORMATION = 0;

    private final FlowerKeywordRepository flowerKeywordRepository;
    private final FlowerRepository flowerRepository;
    private final FlowerExpressionRepository flowerExpressionRepository;

    @Transactional
    public List<FlowerInfoByKeywordResponse> getFlowerInfoByKeyword(final Long keywordId) {
        List<FlowerExpression> expressionCandidates;
        if (keywordId == TOTAL_FLOWER_INFORMATION) {
            expressionCandidates = getAllFlowerExpressions();
        } else {
            expressionCandidates = getExpressionsByKeyword(keywordId);
        }

        return expressionCandidates.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private List<FlowerExpression> getAllFlowerExpressions() {
        List<Flower> flowers = flowerRepository.findAll();
        return flowers.stream()
                .map(flower -> flowerExpressionRepository.findById(flower.getFlowerId())
                        .orElseThrow(() -> new WhoaException(INVALID_FLOWER_AND_EXPRESSION)))
                .collect(Collectors.toList());
    }

    private List<FlowerExpression> getExpressionsByKeyword(Long keywordId) {
        Keyword targetKeyword = flowerKeywordRepository.findByKeywordId(keywordId);
        return flowerExpressionRepository.findByFlowerLanguageContaining(targetKeyword.getKeywordName());
    }

    private FlowerInfoByKeywordResponse mapToResponse(FlowerExpression flowerExpression) {
        Flower flower = flowerExpression.getFlower();
        return new FlowerInfoByKeywordResponse(
                flower.getFlowerName(),
                flower.getFlowerImage(),
                flower.getKeyword().getKeywordName()
        );
    }
}
