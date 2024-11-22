package com.revup.auth.cache;

import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

    private final RedisTemplate<String, RefreshToken> redisTemplate;
    private static final String REFRESH_TOKEN_KEY = "refresh-tokens"; // Redis의 Hash key 이름

    @Override
    public void save(RefreshToken token) {
        HashOperations<String, Long, RefreshToken> hashOps = getTokenHashOps();
        hashOps.put(REFRESH_TOKEN_KEY, token.userId(), token); // userId를 Field로 저장
        log.info("refreshTokens = {}", hashOps.values(REFRESH_TOKEN_KEY));
    }

    @Override
    public RefreshToken remove(Long userId) {
        HashOperations<String, Long, RefreshToken> hashOps = getTokenHashOps();
        RefreshToken token = hashOps.get(REFRESH_TOKEN_KEY, userId);

        if (token != null) {
            hashOps.delete(REFRESH_TOKEN_KEY, userId);
        }
        log.info("refreshTokens = {}", hashOps.values(REFRESH_TOKEN_KEY));
        return token;
    }

    private HashOperations<String, Long, RefreshToken> getTokenHashOps() {
        return redisTemplate.opsForHash();
    }
}
