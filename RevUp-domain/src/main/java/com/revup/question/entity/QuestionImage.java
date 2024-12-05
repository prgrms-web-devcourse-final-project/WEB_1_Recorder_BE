package com.revup.question.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.common.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "question_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionImage extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    private QuestionImage(String imageUrl,
                          String name,
                          Question question) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.question = question;
    }

    public void assignQuestion(Question question){
        this.question = question;
    }
}
