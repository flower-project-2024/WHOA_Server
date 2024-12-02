package com.whoa.whoaserver.domain.member.domain;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import com.whoa.whoaserver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String deviceId;
    private boolean registered;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bouquet> bouquet = new ArrayList<>();

    @Builder
    private Member(String deviceId, boolean registered) {
        this.deviceId = deviceId;
        this.registered = registered;
    }

    public static Member createMember(String deviceId) {
        return Member.builder()
                .registered(true)
                .deviceId(deviceId)
                .build();
    }
}
