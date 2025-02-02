package com.whoa.whoaserver.domain.member.service;

import com.whoa.whoaserver.domain.member.domain.Member;
import com.whoa.whoaserver.domain.member.dto.request.MemberRegisterRequest;
import com.whoa.whoaserver.domain.member.dto.response.MemberInfo;
import com.whoa.whoaserver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import static com.whoa.whoaserver.global.exception.ExceptionCode.EXIST_MEMBER;
import static com.whoa.whoaserver.global.utils.ClientUtils.getClientIP;


import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoa.whoaserver.global.exception.WhoaException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInfo register(MemberRegisterRequest request) {
		String clientIP = getClientIP();

        Optional<Member> optionalMember = memberRepository.findByDeviceId(request.deviceId());

        if (optionalMember.isPresent()) {
            throw new WhoaException(
				EXIST_MEMBER,
				"MemberService register - 이미 같은 디바이스 아이디로 등록되었음(findBy present)",
				clientIP,
				"register deviceId request : " + request.deviceId()
			);
        }

        Member newMember = registerMember(request.deviceId());

        memberRepository.save(newMember);

        return MemberInfo.of(newMember);
    }

    private Member registerMember(String deviceId) {
        return Member.createMember(deviceId);
    }
}
