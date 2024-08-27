package com.whoa.whoaserver.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.member.dto.request.MemberRegisterRequest;
import com.whoa.whoaserver.member.service.MemberService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.whoa.whoaserver.global.exception.ExceptionCode.EXIST_MEMBER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean({JpaMetamodelMappingContext.class})
public class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MemberService memberService;

	@Nested
	class 디바이스등록 {
		private static String TEST_DEVICE_ID = "B353GF4G-E1CB-58B0-0B77-9E47E6G9D362";
		private static String DUPLICATED_DEVICE_ID = "8834046F-06E3-4BF5-AEEA-E3AB5BF6F383";

		@Test
		void 디바이스_등록_요청() throws Exception {
			// Given
			MemberRegisterRequest request = MemberRegisterRequest.builder()
				.deviceId(TEST_DEVICE_ID)
				.build();

			// When
			ResultActions resultActions = mockMvc.perform(post("/api/members/register")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			);

			// Then
			resultActions.andExpect(status().isOk());
		}

		@Test
		void 중복된_디바이스_아이디_등록() throws Exception {
			// Given
			MemberRegisterRequest request = MemberRegisterRequest.builder()
				.deviceId(DUPLICATED_DEVICE_ID)
				.build();

			// MemberServiee의 register 메서드가 DUPLICATED_DEVICE_ID로 호출될 때 WhoaException을 발생하도록
			given(memberService.register(request)).willThrow(new WhoaException(EXIST_MEMBER));

			// When
			ResultActions resultActions = mockMvc.perform(post("/api/members/register")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			);

			// Then
			resultActions.andExpect(status().isConflict());
		}
	}
}
