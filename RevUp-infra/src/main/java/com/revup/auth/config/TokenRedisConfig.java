package com.revup.auth.config;

import com.revup.auth.dto.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;


@Configuration
@RequiredArgsConstructor
public class TokenRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Value("${refresh.expiration_time}")
    private Long storagePeriod;

    @Value("${refresh.key}")
    private String refreshTokenKey;

    @Bean
    public RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate() {
        RedisTemplate<String, RefreshToken> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RefreshToken.class));
        redisTemplate.afterPropertiesSet();
        redisTemplate.expire(refreshTokenKey, storagePeriod, TimeUnit.MILLISECONDS); // 30일 동안 유효
        return redisTemplate;
    }
}
