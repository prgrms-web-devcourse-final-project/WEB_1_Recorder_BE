package com.revup.answer.repository;

import com.revup.answer.entity.AnswerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerImageRepository extends JpaRepository<AnswerImage, Long> {
    @Modifying
    @Query("delete from AnswerImage ai where ai.answer.id = :answerId")
    void deleteByAnswerId(Long answerId);

}
