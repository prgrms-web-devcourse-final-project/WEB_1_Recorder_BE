package com.revup.feedback.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.common.BooleanStatus;
import com.revup.feedback.entity.enums.FeedbackState;
import com.revup.feedback.entity.enums.FeedbackType;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @Enumerated(value = EnumType.STRING)
    private FeedbackType type;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String githubLink;

    @Enumerated(value = EnumType.STRING)
    private BooleanStatus githubLinkReveal;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private FeedbackState state = FeedbackState.WAITING_ACCEPTANCE;


    @OneToMany(mappedBy = "feedback")
    private List<FeedbackCode> feedbackCodes = new ArrayList<>();

    @OneToMany(mappedBy = "feedback")
    private List<FeedbackSkillStack> feedbackSkillStacks = new ArrayList<>();


    @Builder
    private Feedback(User student, User teacher, FeedbackType type, String title, String githubLink, BooleanStatus githubLinkReveal, String description) {
        this.student = student;
        this.teacher = teacher;
        this.type = type;
        this.title = title;
        this.githubLink = githubLink;
        this.githubLinkReveal = githubLinkReveal;
        this.description = description;
    }

    public void updateState(FeedbackState state) {
        this.state = state;
    }

}
