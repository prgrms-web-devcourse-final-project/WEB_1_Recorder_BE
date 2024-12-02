package com.revup.answer.entity;

import com.revup.answer.enums.AdoptedReview;
import com.revup.common.BooleanStatus;
import com.revup.common.SoftDeleteEntity;
import com.revup.question.entity.Question;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_deleted = 'FALSE'")
public class Answer extends SoftDeleteEntity {
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

    @OneToOne(mappedBy = "answer")
    private AnswerCode code;

    @Builder
    private Answer(
            User user,
            String title,
            String content,
            BooleanStatus isAccept,
            AdoptedReview review,
            int goodCount,
            int badCount) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.isAccept = isAccept;
        this.review = review;
        this.goodCount = goodCount;
        this.badCount = badCount;
    }

    public void assignQuestion(Question question) {
        this.question = question;
    }

    public void addAnswerCode(AnswerCode answerCode) {
        this.code = answerCode;
    }


    public void adoptWithReview(AdoptedReview adoptedReview) {
        this.isAccept = BooleanStatus.TRUE;
        this.review = adoptedReview;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
