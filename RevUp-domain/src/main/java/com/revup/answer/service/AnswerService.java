package com.revup.answer.service;

import com.revup.answer.entity.Answer;
import com.revup.answer.repository.AnswerRepository;
import com.revup.image.entity.AnswerImage;
import com.revup.image.repository.AnswerImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerImageRepository answerImageRepository;

    @Transactional
    public Long createAnswer(Answer answer, List<AnswerImage> images) {
        answerRepository.save(answer);
        answerImageRepository.saveAll(images);
        return answer.getId();
    }
}
