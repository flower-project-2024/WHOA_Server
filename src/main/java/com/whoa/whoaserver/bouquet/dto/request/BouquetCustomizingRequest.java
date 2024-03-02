package com.whoa.whoaserver.bouquet.dto.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

public record BouquetCustomizingRequest(
    @NotNull String purpose,
    @NotNull String colorType,
    @NotNull String flowerType,
    @Nullable String wrappingType,
    @NotNull String price,
    @Nullable String requirement,
    @Nullable String imgPath
) {}

