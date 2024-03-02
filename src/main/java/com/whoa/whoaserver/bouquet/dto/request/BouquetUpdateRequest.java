package com.whoa.whoaserver.bouquet.dto.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record BouquetUpdateRequest(
        @NotNull Long bouquetId,
        @NotNull String purpose,
        @NotNull String colorType,
        @NotNull String flowerType,
        @Nullable String wrappingType,
        @NotNull String price,
        @Nullable String requirement,
        @NotNull String imgPath
) {
}
