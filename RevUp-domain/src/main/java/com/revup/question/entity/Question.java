package com.revup.question.entity;

import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.common.SoftDeleteEntity;
import com.revup.question.enums.QuestionState;
import com.revup.question.enums.QuestionType;
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
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_deleted = 'FALSE'")
public class Question extends SoftDeleteEntity {
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

    @Column(length = 500)
    private String githubLink;

    @Enumerated(value = EnumType.STRING)
    private BooleanStatus githubLinkReveal;


    @Enumerated(EnumType.STRING)
    private BooleanStatus isAnonymous;

    private int answerCount;

    private int readCount;

    @ElementCollection(targetClass = SkillStack.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<SkillStack> stacks;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User user;


    @OneToMany(mappedBy = "question")
    private Set<QuestionCode> codes = new HashSet<>();

    @Builder
    private Question(
            String title,
            QuestionType type,
            QuestionState state,
            String content,
            String githubLink,
            BooleanStatus githubLinkReveal,
            BooleanStatus isAnonymous,
            User user,
            Set<SkillStack> stacks
    ) {
        this.title = title;
        this.type = type;
        this.state = state;
        this.content = content;
        this.githubLink = githubLink;
        this.githubLinkReveal = githubLinkReveal;
        this.answerCount = 0;
        this.readCount = 0;
        this.isAnonymous = isAnonymous;
        this.user = user;
        this.stacks = stacks;
    }

    public void update(
            String title,
            QuestionType type,
            String content,
            String githubLink,
            BooleanStatus githubLinkReveal,
            BooleanStatus isAnonymous,
            Set<SkillStack> stacks) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.githubLink = githubLink;
        this.githubLinkReveal = githubLinkReveal;
        this.isAnonymous = isAnonymous;
        this.stacks = stacks;
    }


    public void addQuestionCode(QuestionCode code){
        this.codes.add(code);
    }

    public void increaseAnswerCount() {
        this.answerCount++;
    }

    public void decreaseAnswerCount(){
        this.answerCount--;
    }

    public void increaseReadCount() {
        this.readCount++;
    }

    public void adoptAnswer() {
        this.state = QuestionState.ADOPTED;
    }

}
