package com.whoa.whoaserver.mapping.repository;

import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;

import java.util.List;

public interface FlowerExpressionKeywordRepositoryCustom {
    List<FlowerExpressionKeyword> findByKeywordIdWithExpressions(Long keywordId);
}
