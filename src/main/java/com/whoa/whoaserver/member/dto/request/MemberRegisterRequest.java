package com.whoa.whoaserver.member.dto.request;

import lombok.Builder;

@Builder
public record MemberRegisterRequest(
        String deviceId
) {
}
