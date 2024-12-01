package com.revup.answer.repository;

import com.revup.answer.entity.AnswerCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerCodeRepository extends JpaRepository<AnswerCode, Long> {
    @Modifying
    @Query("delete from AnswerCode ac where ac.answer.id = :answerId")
    void deleteByAnswerId(Long answerId);
}
