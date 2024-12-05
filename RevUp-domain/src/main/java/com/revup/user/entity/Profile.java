package com.revup.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Column(length = 50)
    private String nickname;

    @Column(length = 500)
    private String profileImage;

    @Column(length = 500)
    private String introduction;

    private int totalAnswerCount;

    private int adoptedAnswerCount;

    @Builder
    private Profile(String nickname, String profileImage, String introduction) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.totalAnswerCount = 0;
        this.adoptedAnswerCount = 0;
    }

    public boolean isEmptyIntroduction() {
        return this.introduction == null || this.introduction.isEmpty();
    }

    public void increaseTotalAnswerCount() {
        this.totalAnswerCount++;
    }

    public void increaseAdoptedAnswerCount() {
        this.adoptedAnswerCount++;
    }
}
