package com.revup.auth.cache;

import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.repository.CertificationNumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class EmailCertificationNumberRepository implements CertificationNumberRepository {

    //U
    private final RedisTemplate<String, CertificationNumber> redisTemplate;
    private static final String KEY = "certification-numbers";


    @Override
    public void save(CertificationKey hashKey, CertificationNumber number) {
        HashOperations<String, String, CertificationNumber> numberHashOps = getHashOps();
        numberHashOps.put(KEY, hashKey.key(), number);
    }

    @Override
    public void remove(CertificationKey hashKey) {
        HashOperations<String, String, CertificationNumber> numberHashOps = getHashOps();
        numberHashOps.delete(KEY, hashKey.key());
    }

    @Override
    public Optional<CertificationNumber> findById(CertificationKey hashKey) {
        HashOperations<String, String, CertificationNumber> numberHashOps = getHashOps();
        return Optional.ofNullable(numberHashOps.get(KEY, hashKey.key()));
    }

    private HashOperations<String, String, CertificationNumber> getHashOps() {
        return redisTemplate.opsForHash();
    }
}
