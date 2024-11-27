package com.revup.question.entity;

import com.revup.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "question_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionCode extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    private QuestionCode(Question question, String name, String content) {
        this.question = question;
        this.name = name;
        this.content = content;
        question.addQuestionCode(this);
    }
}
