package com.revup.user.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.user.dto.Email;
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

    @Embedded private Profile profile;

    @Column(length = 50)
    private String businessEmail;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(length = 100)
    private String socialId;

    @Column(length = 50)
    private String socialEmail;

    @Builder
    private User(
                LoginType loginType,
                String socialEmail,
                String socialId) {
        this.profile = createNewProfile();
        this.loginType = loginType;
        this.socialEmail = socialEmail;
        this.socialId = socialId;
    }

    private static Profile createNewProfile() {
        return Profile.builder()
                .profileImage(null)
                .nickname(null)
                .introduction(null)
                .build();
    }

    public void updateProfile(Profile profile) {
        this.profile = profile;
    }

    public String getNickname() {
        return this.profile.getNickname();
    }

    public void updateBusinessEmail(Email email) {
        this.businessEmail = email.value();
    }
}
