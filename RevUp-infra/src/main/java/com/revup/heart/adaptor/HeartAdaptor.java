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
        HeartType heartType = HeartType.from(isGood);
        String userKey = generateUserKey(answerId, heartType);
        String typeKey = generateTypeKey(heartType);
        redisTemplate.opsForSet().add(userKey, userId.toString());
        redisTemplate.opsForSet().add(typeKey, answerId.toString());
    }

    @Override
    public void removeHeart(Long answerId, Long userId, boolean isGood) {
        HeartType heartType = HeartType.from(isGood);
        String userKey = generateUserKey(answerId, heartType);
        String typeKey = generateTypeKey(heartType);

        redisTemplate.opsForSet().remove(userKey, userId.toString());
        redisTemplate.opsForSet().remove(typeKey, answerId.toString());

    }

    @Override
    public HeartType getHeartType(Long answerId, Long userId) {
        String goodKey = generateUserKey(answerId, HeartType.GOOD);
        String badKey = generateUserKey(answerId, HeartType.BAD);

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
        Set<String> answerIds = new HashSet<>();

        Set<String> goodIds = redisTemplate.opsForSet().members(generateTypeKey(HeartType.GOOD));
        Set<String> badIds = redisTemplate.opsForSet().members(generateTypeKey(HeartType.BAD));

        if (goodIds != null) {
            answerIds.addAll(goodIds);
        }

        if (badIds != null) {
            answerIds.addAll(badIds);
        }

        return answerIds.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<String> getGoods(Long answerId) {
        String key = generateUserKey(answerId, HeartType.GOOD);
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Set<String> getBads(Long answerId) {
        String key = generateUserKey(answerId, HeartType.BAD);
        return redisTemplate.opsForSet().members(key);
    }


    private String generateUserKey(Long answerId, HeartType heartType) {
        return answerId.toString() + ":" + heartType.getValue();
    }

    private String generateTypeKey(HeartType heartType) {
        return heartType.getValue();
    }
}
