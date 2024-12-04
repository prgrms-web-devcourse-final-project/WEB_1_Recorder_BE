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
    public Long createHeart(Long answerId, Heart heart, User currentUser) {
        // 답변 조회
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));

        // 중복 체크
        if (heartRepository.existsHeartByAnswerAndUser(answer, currentUser)) {
            throw new HeartExistException();
        }

        // 엔티티 저장
        heart.assignAnswer(answer);
        heartRepository.save(heart);

        // Redis에 반영
        if (heart.isGood()) {
            heartPort.incrementGoodHeart(answerId, currentUser.getId());
        } else {
            heartPort.incrementBadHeart(answerId, currentUser.getId());
        }


        return heartRepository.save(heart).getId();

    }

    @Transactional
    public void deleteHeart(Long id, User currentUser) {

        // 반응 조회
        Heart heart = heartRepository.findByIdWithUserAndAnswer(id)
                .orElseThrow(() -> new HeartNotFoundException(id));

        //권한 체크
        checkPermission(currentUser, heart.getUser());

        Answer answer = heart.getAnswer();

        if (heart.isGood()) {
            heartPort.decrementGoodHeart(answer.getId(),currentUser.getId());
        } else {
            heartPort.decrementBadHeart(answer.getId(), currentUser.getId());
        }

        heart.softDelete();

    }

    @Transactional
    public void updateHeartsCount() {
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

        // Redis 데이터 초기화
        heartPort.clearHearts();
    }

    private void checkPermission(User currenUser, User writer) {
        if (!currenUser.equals(writer)) {
            throw new UserPermissionException();
        }
    }
}
