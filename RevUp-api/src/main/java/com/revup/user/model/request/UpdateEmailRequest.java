package com.revup.user.model.request;

import com.revup.user.dto.Certification;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UpdateEmailRequest(

        @NotEmpty(message = "유효한 인증번호 형식이 아닙니다.")
        String certificationKey,

        @NotEmpty(message = "유효한 인증번호 형식이 아닙니다.")
        String inputNumber,

        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String email
) {
        private CertificationKey toCertificationKey() {
                return new CertificationKey(certificationKey);
        }

        private CertificationNumber toCertificationNumber() {
                return new CertificationNumber(inputNumber);
        }

        public Certification toCertification() {
                return new Certification(
                        toCertificationKey(),
                        toCertificationNumber()
                );
        }
        public com.revup.user.dto.Email toEmail() {
                return new com.revup.user.dto.Email(email);
        }
}
