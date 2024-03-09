package com.whoa.whoaserver.bouquet.dto.request;

public record PresignedUrlRequest(
    Long contentLength,
    String extension,
    String imgName
) {
    
}
