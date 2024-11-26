package com.revup.user.dto;

public record EmailCertificationNumberInfo(
        Email setTo,
        CertificationNumber number,
        String subject
) {
    public static EmailCertificationNumberInfo of(
            String setTo, CertificationNumber number, String subject
    ) {
        return new EmailCertificationNumberInfo(
                new Email(setTo),
                number,
                subject
        );
    }
}
