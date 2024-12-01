package com.revup.answer.repository;

import com.revup.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("select a from Answer a join fetch Question q where a.id = :answerId")
    Optional<Answer> findByIdWithQuestion(Long answerId);

    @Query("select a from Answer a join fetch User u where a.id = :answerId")
    Optional<Answer> findByIdWithUser(Long answerId);
}
