package com.whoa.whoaserver.bouquet.dto.response;

import java.util.List;

public record MultipartFileUploadedUrlResponse(
        List<String> imgList
) {
}
