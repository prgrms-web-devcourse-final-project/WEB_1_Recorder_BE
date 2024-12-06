package com.revup.heart.adaptor;

import com.revup.heart.enums.HeartType;
import com.revup.heart.port.HeartPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HeartAdaptor implements HeartPort {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void addHeart(Long answerId, Long userId, boolean isGood) {
        String key = generateKey(answerId, HeartType.from(isGood));
        redisTemplate.opsForSet().add(key, userId.toString());
    }

    @Override
    public void removeHeart(Long answerId, Long userId, boolean isGood) {
        String key = generateKey(answerId, HeartType.from(isGood));
        redisTemplate.opsForSet().remove(key, userId.toString());
    }

    @Override
    public HeartType getHeartType(Long answerId, Long userId) {
        String goodKey = generateKey(answerId, HeartType.GOOD);
        String badKey = generateKey(answerId, HeartType.BAD);

        // 좋아요 존재하면 good 반환
        if (redisTemplate.opsForSet().isMember(goodKey, userId.toString())) {
            return HeartType.GOOD;
        }

        // 싫어요 존재하면 bad 반환
        if (redisTemplate.opsForSet().isMember(badKey, userId.toString())) {
            return HeartType.BAD;
        }

        return null;
    }

    @Override
    public Set<Long> getAllAnswerIds() {
        Set<String> allKeys = new HashSet<>();
        Set<String> goodKeys = redisTemplate.keys("*:good");
        Set<String> badKeys = redisTemplate.keys("*:bad");

        if (goodKeys != null) {
            allKeys.addAll(goodKeys);
        }

        if (badKeys != null) {
            allKeys.addAll(badKeys);
        }

        return allKeys.stream()
                .map(key -> Long.parseLong(key.split(":")[0]))
                .collect(Collectors.toSet());
    }


    @Override
    public Set<String> getGoods(Long answerId) {
        String key = generateKey(answerId, HeartType.GOOD);
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Set<String> getBads(Long answerId) {
        String key = generateKey(answerId, HeartType.BAD);
        return redisTemplate.opsForSet().members(key);
    }




    private String generateKey(Long answerId, HeartType heartType) {
        return answerId.toString() + ":" + heartType.getValue();
    }
}
