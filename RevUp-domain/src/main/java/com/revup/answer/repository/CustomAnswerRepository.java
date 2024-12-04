package com.revup.answer.repository;

import com.revup.answer.dto.AnswerDto;
import com.revup.answer.entity.Answer;
import com.revup.question.entity.Question;
import com.revup.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface CustomAnswerRepository {

    List<Integer> findGoodCountsByUserId(Long userId);

    List<AnswerDto> findAnswerDtosByUserId(Long userId);

    List<LocalDate> findCreatedAtsByUserId(Long userId);

    List<Answer> findByQuestion(Question question);

    List<Answer> findByUserAndLimit(User currentUser, Long lastId, int size);

}
