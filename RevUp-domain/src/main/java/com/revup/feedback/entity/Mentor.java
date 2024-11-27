package com.revup.feedback.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "mentor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 100)
    private String description;

    @OneToMany(mappedBy = "mentor")
    private List<MentorSkillStack> mentorSkillStacks = new ArrayList<>();

    @Builder
    private Mentor(User user, String description) {
        this.user = user;
        this.description = description;
    }

}
