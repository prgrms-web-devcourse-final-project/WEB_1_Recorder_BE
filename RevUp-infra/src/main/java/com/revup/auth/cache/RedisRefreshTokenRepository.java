package com.revup.auth.cache;

import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

    private final RedisTemplate<String, RefreshToken> redisTemplate;

    @Value("${refresh.key}")
    private String refreshTokenKey;

    @Override
    public void save(RefreshToken token, Long id) {
        HashOperations<String, Long, RefreshToken> hashOps = getTokenHashOps();
        hashOps.put(refreshTokenKey, id, token); // userId를 Field로 저장
        log.info("refreshTokens = {}", hashOps.values(refreshTokenKey));
    }

    @Override
    public void remove(Long userId) {
        HashOperations<String, Long, RefreshToken> hashOps = getTokenHashOps();
        hashOps.delete(refreshTokenKey, userId);
        log.info("refreshTokens = {}", hashOps.values(refreshTokenKey));
    }

    @Override
    public Optional<RefreshToken> findById(Long id) {
        return Optional.ofNullable(getTokenHashOps().get(refreshTokenKey, id));
    }


    private HashOperations<String, Long, RefreshToken> getTokenHashOps() {

        return redisTemplate.opsForHash();
    }
}
