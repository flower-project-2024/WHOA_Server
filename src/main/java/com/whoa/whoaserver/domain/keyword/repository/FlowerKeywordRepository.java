package com.whoa.whoaserver.domain.keyword.repository;

import com.whoa.whoaserver.domain.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlowerKeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByKeywordId(final Long keywordId);
}
