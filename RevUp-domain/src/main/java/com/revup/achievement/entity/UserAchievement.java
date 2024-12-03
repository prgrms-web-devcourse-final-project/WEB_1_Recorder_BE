package com.revup.achievement.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_achievements")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAchievement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @Builder
    private UserAchievement(
            User user,
            Achievement achievement
    ) {
        this.user = user;
        this.achievement = achievement;
    }

    public static UserAchievement of(User user, Achievement achievement) {
        return UserAchievement.builder()
                .achievement(achievement)
                .user(user)
                .build();
    }
}
