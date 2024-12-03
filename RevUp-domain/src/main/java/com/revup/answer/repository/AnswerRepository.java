package com.revup.answer.repository;

import com.revup.answer.entity.Answer;
import com.revup.user.entity.User;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, CustomAnswerRepository {

    @OrderBy()
    List<Answer> findByUser(User user);

    @Query("select a from Answer a join fetch Question q where a.id = :answerId")
    Optional<Answer> findByIdWithQuestion(Long answerId);

    @Query("select a from Answer a join fetch User u where a.id = :answerId")
    Optional<Answer> findByIdWithUser(Long answerId);
}
