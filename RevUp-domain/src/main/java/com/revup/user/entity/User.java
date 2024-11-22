package com.revup.user.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @ToString
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"socialId", "loginType"})
        }
)
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

    public static User from(UserDto userDto) {
        return User.builder()
                .nickname(userDto.nickname())
                .profileImage(userDto.profileImage())
                .loginType(userDto.loginType())
                .socialEmail(userDto.socialEmail())
                .socialId(userDto.socialId())
                .businessEmail(userDto.businessEmail())
                .introduction(userDto.introduction())
                .build();
    }
}
