package com.revup.answer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "answer_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    private AnswerCode(Answer answer, String name, String content) {
        this.answer = answer;
        this.name = name;
        this.content = content;
        answer.addAnswerCode(this);
    }
}
