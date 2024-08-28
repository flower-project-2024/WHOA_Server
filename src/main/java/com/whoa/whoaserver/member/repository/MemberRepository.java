package com.whoa.whoaserver.member.repository;

import com.whoa.whoaserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByDeviceId(String deviceId);
}
