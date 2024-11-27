package com.revup.auth.config;

import com.revup.user.dto.CertificationNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class CertificationNumberConfig {

    private final RedisConnectionFactory certificationNumberConnectionFactory;

    @Bean
    public RedisTemplate<String, CertificationNumber> certificationNumberRedisTemplate() {
        RedisTemplate<String, CertificationNumber> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(certificationNumberConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(CertificationNumber.class));
        redisTemplate.afterPropertiesSet();
        redisTemplate.expire("certification-numbers", 5, TimeUnit.MINUTES);
        return redisTemplate;
    }
}
