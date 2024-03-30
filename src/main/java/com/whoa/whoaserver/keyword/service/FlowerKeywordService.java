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

import static com.whoa.whoaserver.global.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlowerKeywordService {

    private final FlowerKeywordRepository flowerKeywordRepository;
    private final FlowerRepository flowerRepository;
    private final FlowerExpressionRepository flowerExpressionRepository;

    @Transactional
    public List<FlowerInfoByKeywordResponse> getFlowerInfoByKeyword(final Long keywordId) {
        if (keywordId == 0) {
            List<Flower> flowers = flowerRepository.findAll();

            List<FlowerInfoByKeywordResponse> flowerInfoResponse = flowers.stream()
                    .map(flower -> {
                        FlowerExpression flowerExpression = flowerExpressionRepository.findById(flower.getFlowerId())
                                .orElseThrow(() -> new WhoaException(INVALID_FLOWER_AND_EXPRESSION));
                        return new FlowerInfoByKeywordResponse(
                                flower.getFlowerName(),
                                flower.getFlowerImage(),
                                List.of(flowerExpression.getFlowerLanguage())
                        );
                    })
                    .collect(Collectors.toList());
            return flowerInfoResponse;
        } else {

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
}
