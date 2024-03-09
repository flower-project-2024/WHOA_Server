package com.whoa.whoaserver.member.domain;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String deviceId;
    private boolean registered;
    private boolean deleted;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bouquet> bouquet = new ArrayList<>();

    @Builder
    private Member(String deviceId, boolean registered, boolean deleted) {
        this.deviceId = deviceId;
        this.registered = registered;
        this.deleted = deleted;
    }

    public static Member createInitMemberStatus(String deviceId) {
        return Member.builder()
                .registered(true)
                .deviceId(deviceId)
                .build();
    }

    public Long getId() {
        return id;
    }
}