package com.whoa.whoaserver.keyword.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.keyword.dto.response.FlowerInfoByKeywordResponse;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;
import com.whoa.whoaserver.mapping.repository.FlowerExpressionKeywordRepository;
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

    private final FlowerRepository flowerRepository;
    private final FlowerExpressionKeywordRepository flowerExpressionKeywordRepository;

    @Transactional
    public List<FlowerInfoByKeywordResponse> getFlowerInfoByKeyword(final Long keywordId) {
        List<FlowerExpression> flowerExpressionList; // 꽃말 테이블에서 꽃말-꽃 response 응답 처리 부분
        if (keywordId == TOTAL_FLOWER_INFORMATION) {
            flowerExpressionList = getAllFlowerExpressions();
        } else {
            flowerExpressionList = getExpressionsByKeyword(keywordId);
        }

        return flowerExpressionList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<FlowerExpression> getAllFlowerExpressions() { // mapping 테이블에서 꽃말까지 끌고 오는 부분
        List<FlowerExpressionKeyword> mapping = flowerExpressionKeywordRepository.findAll();
        return mapping.stream()
                .map(flowerExpressionKeyword -> flowerExpressionKeyword.getFlowerExpression())
                .collect(Collectors.toUnmodifiableList());
    }

    private List<FlowerExpression> getExpressionsByKeyword(Long keywordId) { // 키워드에서 mapping 테이블 거쳐 꽃말까지 끌고 오는 부분
        List<FlowerExpressionKeyword> mapping = flowerExpressionKeywordRepository.findAllByKeyword_KeywordId(keywordId);

        return mapping.stream()
                .map(FlowerExpressionKeyword::getFlowerExpression)
                .collect(Collectors.toUnmodifiableList());
    }

    private FlowerInfoByKeywordResponse mapToResponse(FlowerExpression flowerExpression) { // 꽃말 넘겨주고 각 꽃말에 대응되는 keyword 리스트까지 전달
        List<String> keywordNames = flowerExpression.getFlowerExpressionKeywords().stream()
                .map(flowerExpressionKeyword -> flowerExpressionKeyword.getKeyword().getKeywordName())
                .collect(Collectors.toUnmodifiableList());

        return FlowerInfoByKeywordResponse.fromFlowerExpressionAndKeyword(flowerExpression, keywordNames);
    }
}
