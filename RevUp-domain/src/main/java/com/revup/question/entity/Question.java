package com.revup.question.entity;

import com.revup.answer.entity.Answer;
import com.revup.common.BaseTimeEntity;
import com.revup.common.BooleanStatus;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Enumerated(EnumType.STRING)
    private QuestionState state;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long readCount;

    @Enumerated(EnumType.STRING)
    private BooleanStatus isAnonymous;

    private int answerCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User user;

    @OneToMany(mappedBy = "question")
    private Set<QuestionTag> questionTags = new HashSet<>();

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @Builder
    private Question(
            String title,
            QuestionType type,
            QuestionState state,
            String content,
            BooleanStatus isAnonymous,
            User user) {
        this.title = title;
        this.type = type;
        this.state = state;
        this.content = content;
        this.readCount = 0L;
        this.answerCount = 0;
        this.isAnonymous = isAnonymous;
        this.user = user;
    }

    public void addQuestionTag(QuestionTag questionTag){
        this.questionTags.add(questionTag);
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
        this.answerCount++;
    }

}
