package com.whoa.whoaserver.bouquet.dto.response;

import java.util.List;

public record BouquetOrderResponse(
	Long bouquetId,
	String bouquetName,
	String createdAt,
	List<String> imgPaths
) {
}
