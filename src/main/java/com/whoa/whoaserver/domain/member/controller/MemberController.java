package com.whoa.whoaserver.domain.member.controller;

import com.whoa.whoaserver.domain.member.dto.request.MemberRegisterRequest;
import com.whoa.whoaserver.domain.member.dto.response.MemberInfo;
import com.whoa.whoaserver.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "Member API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(summary = "멤버 등록", description = "디바이스 등록을 위한 API 입니다.")
    public ResponseEntity<MemberInfo> register(@RequestBody MemberRegisterRequest memberRegisterRequest) {

        MemberInfo response = memberService.register(memberRegisterRequest);
        return ResponseEntity.ok(response);
    }
}
