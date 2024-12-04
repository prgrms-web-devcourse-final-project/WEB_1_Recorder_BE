package com.revup.feedback.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.common.SkillStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: 이거 없애고 ElementCollection 사용으로 리팩토링하기
@Entity
@Getter
@Table(name = "feedback_skill_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackSkillStack extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @Enumerated(value = EnumType.STRING)
    private SkillStack skillStack;

    @Builder
    private FeedbackSkillStack(Feedback feedback, SkillStack skillStack) {
        this.feedback = feedback;
        this.skillStack = skillStack;
    }

}
