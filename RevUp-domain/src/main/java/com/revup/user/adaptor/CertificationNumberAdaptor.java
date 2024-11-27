package com.revup.user.adaptor;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.repository.CertificationNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CertificationNumberAdaptor {

    private final CertificationNumberRepository certificationNumberRepository;

    public CertificationKey save(CertificationNumber number) {
        CertificationKey key = createKey();
        certificationNumberRepository.save(key, number);
        return key;
    }

    public CertificationNumber findByKey(CertificationKey key) {
        return certificationNumberRepository.findById(key)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CERTIFICATION_NUMBER));
    }

    private static CertificationKey createKey() {
        return new CertificationKey(UUID.randomUUID().toString());
    }

    public void deleteByKey(CertificationKey key) {
        certificationNumberRepository.remove(key);
    }
}
