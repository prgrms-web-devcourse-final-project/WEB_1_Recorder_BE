package com.revup.question.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "question_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionTag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    private QuestionTag(Question question, Tag tag) {
        this.question = question;
        this.tag = tag;
    }
}
