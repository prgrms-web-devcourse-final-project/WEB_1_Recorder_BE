package com.revup.user.dto;

public record Certification(
        CertificationKey key,
        CertificationNumber number
) {

    public static Certification of(CertificationKey key, CertificationNumber number) {
        return new Certification(key, number);
    }
}
