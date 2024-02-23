package com.whoa.whoaserver.bouquet.dto.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record BouquetCustomizingRequest(
    @NotNull Long id,
    @NotNull Integer purpose,
    @Nullable String colorType,
    @Nullable String flowerType,
    @Nullable String wrappingType,
    @NotNull Integer price,
    @Nullable String requirement
) {}

