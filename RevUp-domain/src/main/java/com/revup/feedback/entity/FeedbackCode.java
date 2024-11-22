package com.revup.feedback.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "feedback_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackCode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    private FeedbackCode(Feedback feedback, String name, String content) {
        this.feedback = feedback;
        this.name = name;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
