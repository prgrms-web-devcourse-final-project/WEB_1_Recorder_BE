package com.revup.answer.repository;

import com.revup.answer.entity.Answer;
import com.revup.user.entity.User;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, CustomAnswerRepository {

    @OrderBy()
    List<Answer> findByUser(User user);

    @Query("select a from Answer a join fetch a.question q where a.id = :answerId")
    Optional<Answer> findByIdWithQuestion(Long answerId);

    @Query("select a from Answer a join fetch a.user u where a.id = :answerId")
    Optional<Answer> findByIdWithUser(Long answerId);

    @Query("select a from Answer a join fetch a.user u join fetch a.question q where a.id = :answerId")
    Optional<Answer> findByIdWithUserAndQuestion(Long answerId);

    @Modifying
    @Query("update Answer a set a.isDeleted = 'TRUE' where a.question.id = :questionId")
    void softDeleteAnswersByQuestionId(Long questionId);
}
