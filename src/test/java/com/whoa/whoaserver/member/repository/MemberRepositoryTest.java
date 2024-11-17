package com.whoa.whoaserver.member.repository;

import com.whoa.whoaserver.domain.member.repository.MemberRepository;
import com.whoa.whoaserver.global.config.JpaAuditingConfig;
import com.whoa.whoaserver.global.config.QuerydslConfig;
import com.whoa.whoaserver.domain.member.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaAuditingConfig.class, QuerydslConfig.class})
@ActiveProfiles("dev")
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	private static final String TEST_DEVICE_ID = "B353GF4G-E1CB-58B0-0B77-9E47E6G9D362";

	@Test
	void 디바이스_등록에_따라_멤버를_저장() {
		// Given
		Member member = Member.builder()
			.deviceId(TEST_DEVICE_ID)
			.registered(true)
			.build();

		// When
		Member newMember = memberRepository.save(member);

		// Then
		Assertions.assertNotNull(newMember);
		Assertions.assertNotNull(newMember.getId());
		Assertions.assertNotNull(newMember.getCreatedAt());
		Assertions.assertNotNull(newMember.getDeviceId());
	}
}
