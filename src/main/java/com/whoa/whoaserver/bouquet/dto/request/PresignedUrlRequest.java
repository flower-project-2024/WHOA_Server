package com.whoa.whoaserver.bouquet.dto.request;

public record PresignedUrlRequest(
    long contentLength,
    String extension
) {
    
}
