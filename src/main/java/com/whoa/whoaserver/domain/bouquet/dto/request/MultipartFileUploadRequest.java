package com.whoa.whoaserver.domain.bouquet.dto.request;

import jakarta.validation.constraints.NotNull;

public record MultipartFileUploadRequest(
        @NotNull(message = "주문서 등록 이후 반환받은 id로 요청주세요.") Long bouquet_id
) {
}
