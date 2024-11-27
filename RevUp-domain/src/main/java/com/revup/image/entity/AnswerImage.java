package com.revup.image.entity;

import com.revup.answer.entity.Answer;
import com.revup.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "answer_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Builder
    private AnswerImage(
            String imageUrl,
            String name,
            Answer answer) {

        this.imageUrl = imageUrl;
        this.name = name;
        this.answer = answer;
    }
}
