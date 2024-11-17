package com.whoa.whoaserver.domain.mapping.repository;

import com.whoa.whoaserver.domain.mapping.domain.FlowerExpressionKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerExpressionKeywordRepository extends JpaRepository<FlowerExpressionKeyword, Long>, FlowerExpressionKeywordRepositoryCustom {

    List<FlowerExpressionKeyword> findAllByKeyword_KeywordId(Long keywordId);
}
