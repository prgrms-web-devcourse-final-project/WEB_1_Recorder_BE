package com.revup.user.service;

import com.revup.user.adaptor.CertificationNumberAdaptor;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.util.CertificationNumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final CertificationNumberAdaptor certificationNumberAdaptor;

    @Override
    public CertificationNumber createNumber() {
        return CertificationNumberUtil.generateNumber();
    }

    @Override
    public CertificationKey saveCertificationNumber(CertificationNumber number) {
        return certificationNumberAdaptor.save(number);
    }

    @Override
    public void validateCertificationNumber(CertificationKey key, CertificationNumber inputNumber) {
        CertificationNumber savedNumber = certificationNumberAdaptor.findByKey(key);
        savedNumber.validSameValue(inputNumber);
    }

    @Override
    public void deleteNumber(CertificationKey key) {
        certificationNumberAdaptor.deleteByKey(key);
    }

}
