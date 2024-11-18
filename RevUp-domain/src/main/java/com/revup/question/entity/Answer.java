package com.revup.question.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.common.BooleanStatus;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private BooleanStatus isAccept;

    @Enumerated(EnumType.STRING)
    private AdoptedReview review;

    private int goodCount;

    private int badCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    private Answer(
            String title,
            String content,
            BooleanStatus isAccept,
            AdoptedReview review,
            int goodCount,
            int badCount) {
        this.title = title;
        this.content = content;
        this.isAccept = isAccept;
        this.review = review;
        this.goodCount = goodCount;
        this.badCount = badCount;
    }
}
