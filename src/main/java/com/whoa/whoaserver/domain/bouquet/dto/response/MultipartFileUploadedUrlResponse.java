package com.whoa.whoaserver.domain.bouquet.dto.response;

import java.util.List;

public record MultipartFileUploadedUrlResponse(
        List<String> imgList
) {
}
