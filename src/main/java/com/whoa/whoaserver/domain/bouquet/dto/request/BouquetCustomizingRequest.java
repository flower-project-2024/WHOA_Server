package com.whoa.whoaserver.domain.bouquet.dto.request;

import com.whoa.whoaserver.domain.bouquet.domain.type.ColorTypeOption;
import com.whoa.whoaserver.domain.bouquet.domain.type.FlowerSubstitutionTypeOption;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BouquetCustomizingRequest(
    @Nullable String bouquetName,
    @NotNull String purpose,
    @NotNull(message = "단일 색은 ONE_COLOR, 두 가지 색은 TWO_COLOR, 컬러풀은 COLORFULL, 포인트 컬러는 POINT_COLOR로 요청해주세요.") ColorTypeOption colorType,
    @NotNull String colorName,
    @Nullable String pointColor,
    @NotNull String flowerType,
    @NotNull(message = "색감 위주는 FOCUS_ON_COLOR, 꽃말 위주는 FOCUS_ON_FLOWER_LANGUAGE로 요청해주세요.") FlowerSubstitutionTypeOption substitutionType,
    @Nullable String wrappingType,
    @NotNull String price,
    @Nullable String requirement
) {}

