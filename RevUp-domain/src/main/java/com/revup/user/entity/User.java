package com.revup.user.entity;

import com.revup.common.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@ToString
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

    @Embedded
    private Profile profile;

    @Embedded
    private Affiliation affiliation;

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

    public String getProfileImage() {
        return this.profile.getProfileImage();
    }

    public int getTotalAnswerCount() {
        return this.profile.getTotalAnswerCount();
    }

    public BigDecimal getAdoptedRate() {
        int totalAnswerCount = getTotalAnswerCount();
        int adoptedAnswerCount = getAdoptedAnswerCount();
        if (totalAnswerCount == 0)
            return BigDecimal.ZERO;

        BigDecimal numerator = new BigDecimal(adoptedAnswerCount);
        BigDecimal denominator = new BigDecimal(totalAnswerCount);

        return numerator.divide(denominator, 4, RoundingMode.HALF_UP) // 소수점 4자리까지, 반올림
                .multiply(BigDecimal.valueOf(100));
    }

    public int getAdoptedAnswerCount() {
        return this.profile.getAdoptedAnswerCount();
    }

    public void updateAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public void increaseTotalAnswerCount() {
        this.profile.increaseTotalAnswerCount();
    }

    public void increaseAdoptedAnswerCount() {
        this.profile.increaseAdoptedAnswerCount();
    }

    public void decreaseAdoptedAnswerCount() {
        this.profile.decreaseAdoptedAnswerCount();
    }

    public void decreaseTotalAnswerCount() {
        this.profile.decreaseTotalAnswerCount();
    }

    public boolean isFirst() {
        return this.profile.getNickname() == null;
    }
}
