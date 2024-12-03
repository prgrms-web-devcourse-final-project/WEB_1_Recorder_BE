package com.revup.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "email_domains",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"domain"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String domain;

    @Column(length = 100)
    private String name;

    @Builder
    private EmailDomain(
            String domain,
            String name
    ) {
        this.domain = domain;
        this.name = name;
    }
}

