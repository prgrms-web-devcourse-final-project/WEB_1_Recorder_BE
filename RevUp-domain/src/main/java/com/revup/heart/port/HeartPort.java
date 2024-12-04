package com.revup.heart.port;

import java.util.Map;

public interface HeartPort {
    void incrementGoodHeart(Long answerId, Long userId);
    void incrementBadHeart(Long answerId, Long userId);
    void decrementGoodHeart(Long answerId, Long userId);
    void decrementBadHeart(Long answerId, Long userId);
    Map<Long, Map<String, Integer>> getHeartCounts();
    void clearHearts();
}
