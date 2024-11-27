package com.revup.user.dto;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

import java.io.Serializable;

public record CertificationNumber (
        String value
) implements Serializable {

    private boolean isEqualTo(CertificationNumber others) {
        return value.equals(others.value());
    }

    public void validSameValue(CertificationNumber certificationNumber) {
        if(!this.isEqualTo(certificationNumber)) {
            throw new AppException(ErrorCode.INVALID_CERTIFICATION_NUMBER);
        }
    }
}
