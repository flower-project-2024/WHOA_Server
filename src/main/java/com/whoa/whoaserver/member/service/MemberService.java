package com.whoa.whoaserver.member.service;

import lombok.RequiredArgsConstructor;

import static com.whoa.whoaserver.global.exception.ExceptionCode.EXIST_MEMBER;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoa.whoaserver.global.exception.BadRequestException;
import com.whoa.whoaserver.member.domain.Member;
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

        Optional<Member> optionalMember = memberRepository.findByDeviceId(request.deviceId());

        if (optionalMember.isPresent()) {
            throw new BadRequestException(EXIST_MEMBER);
        }

        Member newMember = new Member();
        newMember.init(request.deviceId());

        memberRepository.save(newMember);
        
        return MemberInfo.of(newMember);
    }
}