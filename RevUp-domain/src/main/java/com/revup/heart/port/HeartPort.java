package com.revup.heart.port;

import java.util.Map;

public interface HeartPort {
    Map<Long, Map<String, Integer>> getHeartCounts();

    boolean processLike(Long answerId, Long id, boolean isGood);

    boolean cancelLike(Long answerId, Long userId, boolean isGood);

    void clearHeartCounts();
}
