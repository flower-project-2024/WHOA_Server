package com.whoa.whoaserver.global.dto;

import com.whoa.whoaserver.member.domain.Member;

import lombok.Builder;

@Builder
public record UserContext(Long id) {

    public static UserContext from(Member fromMember) {
        return new UserContext(fromMember.getId());
    }
}
