package com.revup.feedback.entity;

import com.revup.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "feedback_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tag_id")
//    private Tag tag;
    @Column(length = 50)
    private String content;
    // TODO: Tag와 연관관계 설정해야 함. 병합 후 content는 삭제할 예정.

}
