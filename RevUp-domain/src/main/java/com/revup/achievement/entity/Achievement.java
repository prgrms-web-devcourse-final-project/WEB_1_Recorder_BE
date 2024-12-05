package com.revup.achievement.entity;

import com.revup.achieve.AchieveType;
import com.revup.common.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @ToString
@Table(
        name = "achievements",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"type", "standard"})
        },
        indexes = {
                @Index(name = "idx_achievement_type", columnList = "type")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Achievement extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 450)
    private String description;

    @Enumerated(EnumType.STRING)
    private AchieveType type;

    private Integer standard;

    @Builder
    private Achievement(
            String description,
            AchieveType type,
            Integer standard
    ) {
        this.description = description;
        this.type = type;
        this.standard = standard;
    }
}
