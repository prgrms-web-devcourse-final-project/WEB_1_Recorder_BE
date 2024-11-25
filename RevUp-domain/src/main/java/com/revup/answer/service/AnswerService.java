package com.revup.answer.service;

import com.revup.answer.entity.Answer;
import com.revup.answer.repository.AnswerRepository;
import com.revup.image.entity.AnswerImage;
import com.revup.image.repository.AnswerImageRepository;
import com.revup.question.entity.Question;
import com.revup.question.exception.QuestionNotFoundException;
import com.revup.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerImageRepository answerImageRepository;

    @Transactional
    public Long createAnswer(Long questionId, Answer answer, List<AnswerImage> images) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        answer.assignQuestion(question);

        question.increaseAnswerCount();

        answerRepository.save(answer);

        answerImageRepository.saveAll(images);

        return answer.getId();
    }
}
