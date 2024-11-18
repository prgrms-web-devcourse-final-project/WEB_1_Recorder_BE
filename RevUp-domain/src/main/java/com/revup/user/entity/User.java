package com.revup.user.entity;

import com.revup.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nickname;

    @Column(length = 500)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(length = 100)
    private String socialId;

    @Column(length = 50)
    private String socialEmail;

    @Column(length = 50)
    private String businessEmail;

    @Column(length = 500)
    private String introduction;

    @Builder
    private User(String nickname,
                String profileImage,
                LoginType loginType,
                String socialEmail,
                String socialId,
                String businessEmail,
                String introduction) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.loginType = loginType;
        this.socialEmail = socialEmail;
        this.socialId = socialId;
        this.businessEmail = businessEmail;
        this.introduction = introduction;
    }

}
