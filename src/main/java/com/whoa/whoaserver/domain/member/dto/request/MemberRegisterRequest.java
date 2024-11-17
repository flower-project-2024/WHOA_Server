package com.whoa.whoaserver.domain.member.dto.request;

import lombok.Builder;

@Builder
public record MemberRegisterRequest(
        String deviceId
) {
}
