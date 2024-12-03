package com.revup.user.entity;

import com.revup.user.dto.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Affiliation {

    @Column(length = 50)
    private String businessEmail;

    @Column(length = 100)
    private String affiliationName;

    @Builder
    private Affiliation(String businessEmail, String affiliationName) {
        this.businessEmail = businessEmail;
        this.affiliationName = affiliationName;
    }

    public static Affiliation of(Email email, EmailDomain domain) {
        return Affiliation.builder()
                .businessEmail(email.value())
                .affiliationName(domain.getName())
                .build();
    }
}
