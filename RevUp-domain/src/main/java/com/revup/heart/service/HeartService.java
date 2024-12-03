package com.revup.heart.service;

import com.revup.answer.entity.Answer;
import com.revup.answer.exception.AnswerNotFoundException;
import com.revup.answer.repository.AnswerRepository;
import com.revup.heart.entity.Heart;
import com.revup.heart.exception.HeartExistException;
import com.revup.heart.repository.HeartRepository;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public Long createHeart(Long answerId, Heart heart, User currentUser) {
        // 답변 조회
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));

        // 이미 해당 답변과 유저에 대한 반응이 존재하면 에러 반환
        if (heartRepository.existsHeartByAnswerAndUser(answer, currentUser)) {
            throw new HeartExistException();
        }

        heart.assignAnswer(answer);

        if (heart.isGood()) {
            answer.increaseGoodCount();
        } else {
            answer.increaseBadCount();
        }


        return heartRepository.save(heart).getId();

    }

}
