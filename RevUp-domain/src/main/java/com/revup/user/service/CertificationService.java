package com.revup.user.service;

import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;

public interface CertificationService {

    CertificationNumber createNumber();

    CertificationKey saveCertificationNumber(CertificationNumber number);

    void validateCertificationNumber(CertificationKey key, CertificationNumber inputNumber);


    void deleteNumber(CertificationKey key);
}
