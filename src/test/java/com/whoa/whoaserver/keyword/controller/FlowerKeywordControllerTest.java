package com.whoa.whoaserver.keyword.controller;

import com.whoa.whoaserver.domain.keyword.controller.FlowerKeywordController;
import com.whoa.whoaserver.domain.keyword.dto.response.FlowerInfoByKeywordResponse;
import com.whoa.whoaserver.domain.keyword.service.FlowerKeywordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlowerKeywordController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean({JpaMetamodelMappingContext.class})
public class FlowerKeywordControllerTest {

	private static int FINAL_FLOWER_KEYWORD_NOT_EXIST_FLAG = 13;
	private static Long TEST_FLOWER_ID_1 = 132L;
	private static String TEST_FLOWER_NAME_1 = "캄파눌라";
	private static String TEST_FLOWER_LANGUAGE_1 = "따뜻한 사랑, 감사, 성실, 정의, 신념, 절개";
	private static String TEST_FLOWER_IMAGE_URL_1 = "https://whoa-bucket.s3.ap-northeast-2.amazonaws.com/flower/ded6421f-e6a8-4c32-bc47-295122ff7eaf%EC%BA%84%ED%8C%8C%EB%88%8C%EB%9D%BC_%EB%B3%B4.png";
	private static Long TEST_FLOWER_ID_2 = 96L;
	private static String TEST_FLOWER_NAME_2 = "알스트로메리아";
	private static String TEST_FLOWER_LANGUAGE_2 = "행복한 나날, 새로운 만남, 새로운 시작";
	private static String TEST_FLOWER_IMAGE_URL_2 = "https://whoa-bucket.s3.ap-northeast-2.amazonaws.com/flower/885af307-97b8-4b4a-b72e-cea1b37ecb3e%EC%95%8C%EC%8A%A4%ED%8A%B8%EB%A1%9C%EB%A9%94%EB%A6%AC%EC%95%84_%EB%B9%A8.png";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FlowerKeywordService flowerKeywordService;

	@Test
	public void 키워드_아이디_랜덤으로_주어졌을_때_꽃_정보_반환() throws Exception {
		// Given
		Random random = new Random();
		Long randomKeywordId = (long) random.nextInt(FINAL_FLOWER_KEYWORD_NOT_EXIST_FLAG);

		FlowerInfoByKeywordResponse response1 = FlowerInfoByKeywordResponse.builder()
			.id(TEST_FLOWER_ID_1)
			.flowerName(TEST_FLOWER_NAME_1)
			.flowerLanguage(TEST_FLOWER_LANGUAGE_1)
			.flowerImageUrl(TEST_FLOWER_IMAGE_URL_1)
			.flowerKeyword(Arrays.asList("사랑", "감사", "존경"))
			.build();

		FlowerInfoByKeywordResponse response2 = FlowerInfoByKeywordResponse.builder()
			.id(TEST_FLOWER_ID_2)
			.flowerName(TEST_FLOWER_NAME_2)
			.flowerLanguage(TEST_FLOWER_LANGUAGE_2)
			.flowerImageUrl(TEST_FLOWER_IMAGE_URL_2)
			.flowerKeyword(Arrays.asList("응원", "행복"))
			.build();

		List<FlowerInfoByKeywordResponse> mockFlowerInfoList = Arrays.asList(response1, response2);


		when(flowerKeywordService.getFlowerInfoByKeyword(randomKeywordId)).thenReturn(mockFlowerInfoList);

		// When
		ResultActions resultActions = mockMvc.perform(get("/api/flower/keyword/{keywordId}", randomKeywordId)
			.contentType(MediaType.APPLICATION_JSON));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}


}
