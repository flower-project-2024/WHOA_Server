package com.whoa.whoaserver.domain.mapping.repository;

import com.whoa.whoaserver.domain.mapping.domain.CustomizingPurposeKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomizingPurposeKeywordRepository extends JpaRepository<CustomizingPurposeKeyword, Long> {

	List<CustomizingPurposeKeyword> findAllByCustomizingPurpose_CustomizingPurposeIdAndKeyword_KeywordId(Long customizingPurposeId, Long keywordId);

	List<CustomizingPurposeKeyword> findAllByCustomizingPurpose_CustomizingPurposeId(Long customizingPurposeId);
}
