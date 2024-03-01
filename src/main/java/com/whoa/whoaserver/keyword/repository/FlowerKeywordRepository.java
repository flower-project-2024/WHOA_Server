package com.whoa.whoaserver.keyword.repository;

import com.whoa.whoaserver.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlowerKeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByKeywordId(final Long keywordId);
}
