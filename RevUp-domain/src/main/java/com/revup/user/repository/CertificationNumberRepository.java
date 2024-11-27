package com.revup.user.repository;

import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;

import java.util.Optional;

public interface CertificationNumberRepository {

    void save(CertificationKey key, CertificationNumber token);

    void remove(CertificationKey key);

    Optional<CertificationNumber> findById(CertificationKey key);
}
