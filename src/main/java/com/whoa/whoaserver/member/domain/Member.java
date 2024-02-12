package com.whoa.whoaserver.member.domain;

import com.whoa.whoaserver.global.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.whoa.whoaserver.global.exception.ExceptionCode.EXIST_MEMBER;

@Entity
@Table(name = "members")
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String deviceId;
    private boolean registered;
    private boolean deleted;

    @Builder
    private Member(String deviceId, boolean registered, boolean deleted) {
        this.deviceId = deviceId;
        this.registered = registered;
        this.deleted = deleted;
    }

    public void init(String deviceId) {
        if (registered) {
            throw new BadRequestException(EXIST_MEMBER);
        }
        this.registered = true;
        this.deviceId = deviceId;
    }

    public Long getId() {
        return id;
    }
}