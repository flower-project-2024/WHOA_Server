package com.whoa.whoaserver.domain.bouquet.dto.request;

import jakarta.validation.constraints.NotNull;

public record BouquetNameUpdateRequest(
	@NotNull String bouquetName
) { }
