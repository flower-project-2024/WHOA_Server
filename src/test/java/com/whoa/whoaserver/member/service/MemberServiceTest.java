package com.whoa.whoaserver.member.service;

import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.member.domain.Member;
import com.whoa.whoaserver.member.dto.request.MemberRegisterRequest;
import com.whoa.whoaserver.member.dto.response.MemberInfo;
import com.whoa.whoaserver.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Nested
	@DisplayName("디바이스 기기 등록 테스트")
	class RegisterTest {
		private static String TEST_DEVICE_ID = "C464HG5H-F2DC-69C1-1C88-0F58F7H0E473";
		private MemberRegisterRequest request;

		@BeforeEach
		void setRegisterRequest() {
			request = new MemberRegisterRequest(TEST_DEVICE_ID);
		}

		@Test
		void 디바이스_기기_등록_성공() {
			// Given
			given(memberRepository.findByDeviceId(TEST_DEVICE_ID)).willReturn(Optional.empty());

			// When
			MemberInfo memberInfo = memberService.register(request);

			// Then
			Assertions.assertNotNull(memberInfo);
			Assertions.assertEquals(TEST_DEVICE_ID, memberInfo.deviceId());
		}

		@Test
		void 중복된_디바이스_기기_아이디_등록() {
			// Given
			Member existingMember = Member.createInitMemberStatus(TEST_DEVICE_ID);
			given(memberRepository.findByDeviceId(TEST_DEVICE_ID)).willReturn(Optional.of(existingMember));

			// When
			WhoaException e =
				Assertions.assertThrows(
					WhoaException.class, () -> memberService.register(request));

			// Then
			Assertions.assertEquals(ExceptionCode.EXIST_MEMBER, e.getExceptionCode());
		}
	}
}
