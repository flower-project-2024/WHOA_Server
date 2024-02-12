package com.whoa.whoaserver.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByDeviceId(String deviceId);

    @Query("SELECT m FROM Member m WHERE m.id = :memberId AND m.deleted = false")
    Optional<Member> findMemberIncludingUnregisteredById(@Param(value = "memberId") Long memberId);
}