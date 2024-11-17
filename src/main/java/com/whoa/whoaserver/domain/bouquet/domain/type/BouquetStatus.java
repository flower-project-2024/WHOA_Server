package com.whoa.whoaserver.domain.bouquet.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BouquetStatus {

	COMPLETED("제작 완료"),
	INCOMPLETED("저장 완료");

	private final String value;
}
