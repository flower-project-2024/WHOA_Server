package com.whoa.whoaserver.bouquet.dto.response;

import java.util.List;

public record BouquetOrderResponseV2(
	Long bouquetId,
	String bouquetName,
	String createdAt,
	List<String> imgPaths,
	String bouquetStatus
) {
}
