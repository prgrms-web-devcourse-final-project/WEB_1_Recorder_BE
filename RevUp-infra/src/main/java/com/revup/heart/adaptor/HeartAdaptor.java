package com.revup.heart.adaptor;

import com.revup.heart.port.HeartPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HeartAdaptor implements HeartPort {

    private final RedisTemplate<String, String> redisTemplate;
    // 좋아요/싫어요 카운트
    private static final String HEART_COUNTS_KEY = "heart_counts";
    // 중복 방지를 위한 유저-답변 키 저장
    private static final String USER_HEART_KEY = "user_hearts";


    @Override
    public boolean processLike(Long answerId, Long userId, boolean isGood) {
        String userAnswerKey = userId + ":" + answerId;
        String key = USER_HEART_KEY + answerId;

        // 중복확인 이미 좋아요/싫어요를 누른 경우 false
        if (existInSet(key, userAnswerKey)) {
            return false;
        }

        redisTemplate.opsForSet().add(key, userAnswerKey);

        redisTemplate.opsForHash().increment(HEART_COUNTS_KEY, answerId + ":" + (isGood ? "good" : "bad"), 1);

        return true;

    }

    @Override
    public boolean cancelLike(Long answerId, Long userId, boolean isGood) {
        String userAnswerKey = userId + ":" + answerId;
        String key = USER_HEART_KEY + answerId;

        // 존재하지 않으면 false 삭제 할 것 없음
        if (!existInSet(key, userAnswerKey)) {
            return false;
        }

        redisTemplate.opsForSet().remove(key, userAnswerKey);
        redisTemplate.opsForHash().increment(HEART_COUNTS_KEY, answerId + ":" + (isGood ? "good" : "bad"), -1);
        return true;
    }


    @Override
    public Map<Long, Map<String, Integer>> getHeartCounts() {
        // entries: "answerId:type" -> count 형태의 키-값 쌍
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(HEART_COUNTS_KEY);

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
    public void clearHeartCounts() {
        redisTemplate.delete(HEART_COUNTS_KEY);
    }

    private boolean existInSet(String setKey, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(setKey, value));
    }


}