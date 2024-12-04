package com.revup.user.entity;


import com.revup.common.BaseTimeEntity;
import com.revup.common.SkillStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSkillStack extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private SkillStack stack;

    @Builder
    private UserSkillStack(User user, SkillStack stack) {
        this.user = user;
        this.stack = stack;
    }

    public static UserSkillStack of(User user, SkillStack skillStack) {
        return UserSkillStack.builder()
                .user(user)
                .stack(skillStack)
                .build();
    }
}
