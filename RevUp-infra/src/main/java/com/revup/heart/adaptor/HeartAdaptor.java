package com.revup.heart.adaptor;

import com.revup.heart.port.HeartPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class HeartAdaptor implements HeartPort {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String HEART_KEY = "heart_counts";
    private static final String USER_HEART_KEY = "user_hearts";

    @Override
    public void incrementGoodHeart(Long answerId, Long userId) {
        String field = generateField(answerId, userId);
        if (!isDuplicate(field)) { // 중복 체크
            redisTemplate.opsForHash().increment(HEART_KEY, generateAnswerField(answerId, true), 1);
            redisTemplate.opsForSet().add(USER_HEART_KEY, field);
        }
    }

    @Override
    public void incrementBadHeart(Long answerId, Long userId) {
        String field = generateField(answerId, userId);
        if (!isDuplicate(field)) { // 중복 체크
            redisTemplate.opsForHash().increment(HEART_KEY, generateAnswerField(answerId, false), 1);
            redisTemplate.opsForSet().add(USER_HEART_KEY, field);
        }
    }

    @Override
    public void decrementGoodHeart(Long answerId, Long userId) {
        String field = generateField(answerId, userId);
        if (isDuplicate(field)) { // 필드가 존재하면 삭제
            redisTemplate.opsForHash().increment(HEART_KEY, generateAnswerField(answerId, true), -1);
            redisTemplate.opsForSet().remove(USER_HEART_KEY, field);
        }
    }

    @Override
    public void decrementBadHeart(Long answerId, Long userId) {
        String field = generateField(answerId, userId);
        if (isDuplicate(field)) { // 필드가 존재하면 삭제
            redisTemplate.opsForHash().increment(HEART_KEY, generateAnswerField(answerId, false), -1);
            redisTemplate.opsForSet().remove(USER_HEART_KEY, field);
        }
    }


    @Override
    public Map<Long, Map<String, Integer>> getHeartCounts() {
        // entries: "answerId:type" -> count 형태의 키-값 쌍
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(HEART_KEY);

        // 결과 저장할 map
        // key: answerId(Long), value: { "good": count, "bad": count } 형태의 Map
        Map<Long, Map<String, Integer>> result = new HashMap<>();

        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            // answerId:type
            String field = (String) entry.getKey();
            Integer count = Integer.valueOf(entry.getValue().toString());

            // ":"로 분리하여 answerId와 type 추출
            String[] parts = field.split(":");
            Long answerId = Long.valueOf(parts[0]);
            String type = parts[1];

            // 결과 Map에 데이터 저장
            // answerId에 해당하는 서브 맵(Map<String, Integer>)을 가져오거나 생성한 후 값을 추가
            result.computeIfAbsent(answerId, k -> new HashMap<>()).put(type, count);
        }
        return result;
    }

    @Override
    public void clearHearts() {
        redisTemplate.delete(HEART_KEY);
        redisTemplate.delete(USER_HEART_KEY); // 중복 방지 데이터 초기화
    }


    private boolean isDuplicate(String field) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(USER_HEART_KEY, field));
    }


    private String generateField(Long answerId, Long userId) {
        return answerId + ":" + userId;
    }


    private String generateAnswerField(Long answerId, boolean isGood) {
        return answerId + ":" + (isGood ? "good" : "bad");
    }
}