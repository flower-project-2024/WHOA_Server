package com.whoa.whoaserver.bouquet.dto.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record BouquetCustomizingRequest(
    @NotNull Integer purpose,
    @NotNull String colorType,
    @NotNull String flowerType,
    @Nullable String wrappingType,
    @NotNull Integer price,
    @Nullable String requirement
) {}

