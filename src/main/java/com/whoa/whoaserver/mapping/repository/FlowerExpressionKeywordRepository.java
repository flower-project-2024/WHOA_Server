package com.whoa.whoaserver.mapping.repository;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerExpressionKeywordRepository extends JpaRepository<FlowerExpressionKeyword, Long> {

    List<FlowerExpressionKeyword> findAllByKeyword_KeywordId(Long keywordId);
}
