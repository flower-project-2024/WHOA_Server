package com.whoa.whoaserver.mapping.repository;

import com.whoa.whoaserver.mapping.domain.CustomizingPurposeKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomizingPurposeKeywordRepository extends JpaRepository<CustomizingPurposeKeyword, Long> {

	List<CustomizingPurposeKeyword> findAllByCustomizingPurpose_CustomizingPurposeIdAndKeyword_KeywordId(Long customizingPurposeId, Long keywordId);

	List<CustomizingPurposeKeyword> findAllByCustomizingPurpose_CustomizingPurposeId(Long customizingPurposeId);
}
