package com.whoa.whoaserver.member.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whoa.whoaserver.member.domain.Member;

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
