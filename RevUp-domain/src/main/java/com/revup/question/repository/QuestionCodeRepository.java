package com.revup.question.repository;

import com.revup.question.entity.QuestionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionCodeRepository extends JpaRepository<QuestionCode, Long> {
    @Modifying
    @Query("delete from QuestionCode  qc where qc.question.id = :id")
    void deleteByQuestionId(Long id);
}
