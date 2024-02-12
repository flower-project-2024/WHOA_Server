package com.whoa.whoaserver.member.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoa.whoaserver.member.domain.MemberRepository;
import com.whoa.whoaserver.member.dto.request.MemberRegisterRequest;
import com.whoa.whoaserver.member.dto.response.MemberInfo;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberInfo register(MemberRegisterRequest request) {
        return MemberInfo.of(null);
    }
}
