package com.revup.user.entity;

import com.revup.common.SoftDeleteEntity;
import com.revup.user.dto.Email;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter @ToString
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"socialId", "loginType"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_deleted = 'FALSE'")
public class User extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded private Profile profile;

    @Embedded private Affiliation affiliation;

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

    public void updateAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }
}
