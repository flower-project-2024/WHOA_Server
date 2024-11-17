package com.whoa.whoaserver.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whoa.whoaserver.domain.member.domain.Member;

public record MemberInfo(
        Long id,
        String deviceId,

        @JsonIgnore
        boolean registered
) {

    public static MemberInfo of(Member member) {
        return new MemberInfo(member.getId(), member.getDeviceId(), member.isRegistered());
    }
}
