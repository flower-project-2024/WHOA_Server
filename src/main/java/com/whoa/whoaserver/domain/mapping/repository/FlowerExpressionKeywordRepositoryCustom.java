package com.whoa.whoaserver.domain.mapping.repository;

import com.whoa.whoaserver.domain.mapping.domain.FlowerExpressionKeyword;

import java.util.List;

public interface FlowerExpressionKeywordRepositoryCustom {
    List<FlowerExpressionKeyword> findByKeywordIdWithExpressions(Long keywordId);
}
