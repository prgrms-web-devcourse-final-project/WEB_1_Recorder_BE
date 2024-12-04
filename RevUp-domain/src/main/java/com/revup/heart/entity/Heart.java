package com.revup.heart.entity;

import com.revup.answer.entity.Answer;
import com.revup.common.BaseTimeEntity;
import com.revup.common.SoftDeleteEntity;
import com.revup.heart.enums.HeartType;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "heart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_deleted = 'FALSE'")
public class Heart extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private HeartType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Builder
    private Heart(User user, HeartType type) {
        this.user = user;
        this.type = type;
    }

    public void assignAnswer(Answer answer){
        this.answer = answer;
    }

    public boolean isGood(){
        return this.type.isGoodType();
    }
}
