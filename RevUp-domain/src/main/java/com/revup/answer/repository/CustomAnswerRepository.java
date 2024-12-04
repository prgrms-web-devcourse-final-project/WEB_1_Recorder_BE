package com.revup.answer.repository;

import com.revup.answer.dto.AnswerDto;
import com.revup.answer.entity.Answer;
import com.revup.question.entity.Question;

import java.time.LocalDate;
import java.util.List;

public interface CustomAnswerRepository {

    List<Integer> findGoodCountsByUserId(Long userId);

    List<AnswerDto> findAnswerDtosByUserId(Long userId);

    List<LocalDate> findCreatedAtsByUserId(Long userId);

    List<Answer> findByQuestion(Question question);
}
