package com.revup.feedback.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.common.SkillStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "mentor_skill_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorSkillStack extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Enumerated(value = EnumType.STRING)
    private SkillStack skillStack;

    @Builder
    private MentorSkillStack(Mentor mentor, SkillStack skillStack) {
        this.mentor = mentor;
        this.skillStack = skillStack;
    }

}
