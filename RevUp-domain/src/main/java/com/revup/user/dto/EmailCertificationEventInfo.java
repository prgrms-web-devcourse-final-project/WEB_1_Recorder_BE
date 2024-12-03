package com.revup.user.dto;

public record EmailCertificationEventInfo(
        Email setTo,
        CertificationNumber number,
        String subject
) {
    public static EmailCertificationEventInfo of(
            String setTo, CertificationNumber number, String subject
    ) {
        return new EmailCertificationEventInfo(
                new Email(setTo),
                number,
                subject
        );
    }
}
