package com.revup.heart.service;

import com.revup.answer.entity.Answer;
import com.revup.answer.exception.AnswerNotFoundException;
import com.revup.answer.repository.AnswerRepository;
import com.revup.heart.entity.Heart;
import com.revup.heart.exception.HeartExistException;
import com.revup.heart.exception.HeartNotFoundException;
import com.revup.heart.port.HeartPort;
import com.revup.heart.repository.HeartRepository;
import com.revup.user.entity.User;
import com.revup.user.exception.UserPermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final AnswerRepository answerRepository;
    private final HeartPort heartPort;

    @Transactional
    public Long processHeart(Long answerId, Heart heart, User currentUser) {

        boolean isGood = heart.isGood();

        // 중복 체크
        if (!heartPort.processLike(answerId,currentUser.getId(),isGood)) {
            throw new HeartExistException();
        }

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));

        heart.assignAnswer(answer);


        return heartRepository.save(heart).getId();

    }

    @Transactional
    public void cancelHeart(Long answerId, User currentUser, boolean isGood) {
        // Redis 에서 삭제 처리
        if (!heartPort.cancelLike(answerId, currentUser.getId(), isGood)) {
            throw new HeartNotFoundException();
        }

        // DB에서 Soft Delete
        heartRepository.findByAnswerIdAndUserId(answerId, currentUser.getId())
                .ifPresent(Heart::softDelete);

    }



    @Transactional
    public void syncHeartsCount() {
        // Redis에서 추천/비추천 데이터 조회
        Map<Long, Map<String, Integer>> heartCounts = heartPort.getHeartCounts();

        // Redis 데이터를 순회하며 DB에 반영
        heartCounts.forEach((answerId, counts) -> {
            Answer answer = answerRepository.findById(answerId)
                    .orElseThrow(() -> new AnswerNotFoundException(answerId));

            // 추천과 비추천 수를 각각 증가
            Integer goodCount = counts.getOrDefault("good", 0);
            Integer badCount = counts.getOrDefault("bad",0);

            answer.updateGoodCount(goodCount);
            answer.updateBadCount(badCount);
        });

        heartPort.clearHeartCounts();
    }

}
