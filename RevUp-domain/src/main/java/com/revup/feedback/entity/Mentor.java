package com.revup.feedback.entity;

import com.revup.common.SkillStack;
import com.revup.common.SoftDeleteEntity;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;

@Entity
@Getter
@Table(name = "mentor")
@SQLRestriction("is_deleted = 'FALSE'")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentor extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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
