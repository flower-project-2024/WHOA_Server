package com.whoa.whoaserver.member.service;

import lombok.RequiredArgsConstructor;

import static com.whoa.whoaserver.global.exception.ExceptionCode.EXIST_MEMBER;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.member.domain.Member;
import com.whoa.whoaserver.member.domain.MemberRepository;
import com.whoa.whoaserver.member.dto.request.MemberRegisterRequest;
import com.whoa.whoaserver.member.dto.response.MemberInfo;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInfo register(MemberRegisterRequest request) {

        Optional<Member> optionalMember = memberRepository.findByDeviceId(request.deviceId());

        if (optionalMember.isPresent()) {
            throw new WhoaException(EXIST_MEMBER);
        }

        Member newMember = registerMember(request.deviceId());

        memberRepository.save(newMember);

        return MemberInfo.of(newMember);
    }

    private Member registerMember(String deviceId) {
        return Member.createInitMemberStatus(deviceId);
    }
}
