package com.revup.question.repository;

import com.revup.question.entity.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> {
    @Modifying
    @Query("delete from QuestionImage qi where qi.question.id = :id")
    void deleteByQuestionId(Long id);

}
