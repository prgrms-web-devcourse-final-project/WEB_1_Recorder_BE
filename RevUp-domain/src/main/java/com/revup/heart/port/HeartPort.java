package com.revup.heart.port;

import com.revup.heart.enums.HeartType;

import java.util.Set;

public interface HeartPort {


    void addHeart(Long answerId, Long userId, boolean isGood);

    void removeHeart(Long answerId, Long userId, boolean isGood);

    HeartType getHeartType(Long answerId, Long userId);

    Set<String> getGoods(Long answerId);

    Set<String> getBads(Long answerId);

    Set<Long> getAllAnswerIds();


}
