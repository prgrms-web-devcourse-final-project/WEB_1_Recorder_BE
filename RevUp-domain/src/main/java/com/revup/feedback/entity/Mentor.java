package com.revup.feedback.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.common.SkillStack;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "mentor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String content;

    @ElementCollection(targetClass = SkillStack.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<SkillStack> stacks;

    @Builder
    private Mentor(User user, String title, String content, Set<SkillStack> stacks) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.stacks = stacks;
    }

}
