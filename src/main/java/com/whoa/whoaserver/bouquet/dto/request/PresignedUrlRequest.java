package com.whoa.whoaserver.bouquet.dto.request;

import jakarta.validation.constraints.NotNull;

public record PresignedUrlRequest(
    Long contentLength,
    String extension,
    @NotNull String bouquetName,
    @NotNull String imgName
) {
    
}
