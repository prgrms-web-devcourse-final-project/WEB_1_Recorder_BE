package com.revup.heart.service;

import com.revup.answer.entity.Answer;
import com.revup.answer.exception.AnswerNotFoundException;
import com.revup.answer.repository.AnswerRepository;
import com.revup.heart.port.HeartPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartService {
    private final HeartPort heartPort;
    private final AnswerRepository answerRepository;

    public void addHeart(Long answerId, Long userId, boolean isGood) {
        heartPort.addHeart(answerId, userId, isGood);
    }

    public void removeHeart(Long answerId,Long userId, boolean isGood){
        heartPort.removeHeart(answerId, userId, isGood);
    }

    public String getState(Long answerId, Long id) {
        return heartPort.getHeartType(answerId, id);
    }

    @Transactional
    public void syncHeartToAnswer() {

        Set<Long> answerIds = heartPort.getAllAnswerIds();

        for (Long answerId : answerIds) {
            // 좋아요와 싫어요 데이터를 Redis에서 가져오기
            Set<String> goods = heartPort.getGoods(answerId);
            Set<String> bads = heartPort.getBads(answerId);

            Answer answer = answerRepository.findById(answerId)
                    .orElseThrow(() -> new AnswerNotFoundException(answerId));

            answer.updateGoodUsers(goods);
            answer.updateBadUsers(bads);
        }
    }

    public int getGoodCount(Long answerId) {
        return heartPort.getGoods(answerId).size();
    }

    public int getBadCount(Long answerId) {
        return heartPort.getBads(answerId).size();
    }
}
