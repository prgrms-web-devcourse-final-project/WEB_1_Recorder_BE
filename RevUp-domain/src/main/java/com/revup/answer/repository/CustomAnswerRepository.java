package com.revup.answer.repository;

import com.revup.answer.dto.AnswerDto;

import java.time.LocalDate;
import java.util.List;

public interface CustomAnswerRepository {

    List<Integer> findGoodCountsByUserId(Long userId);

    List<AnswerDto> findAnswerDtosByUserId(Long userId);

    List<LocalDate> findCreatedAtsByUserId(Long userId);
}
