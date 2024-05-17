package com.whoa.whoaserver.bouquet.dto.request;

import com.whoa.whoaserver.bouquet.domain.type.colorTypeOption;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BouquetCustomizingRequest(
    @Nullable String bouquetName,
    @NotNull String purpose,
    @NotNull(message = "단일 색은 ONE_COLOR, 두 가지 색은 TWO_COLOR, 컬러풀은 COLORFULL로 요청해주세요.") colorTypeOption colorType,
    @NotNull String colorName,
    @Nullable String pointColor,
    @NotNull String flowerType,
    @Nullable String wrappingType,
    @NotNull String price,
    @Nullable String requirement
) {}

