package com.revup.question.repository;

import com.revup.question.entity.Question;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select q from Question q where q.id = :id")
    Optional<Question> findByIdWithOptimisticLock(Long id);

    @Query(value = "SELECT stacks " +
            "FROM question_stacks " +
            "GROUP BY stacks " +
            "ORDER BY COUNT(stacks) DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<String> findTop5StacksNative();

    @Query("select q from Question q join fetch q.user u where q.id = :questionId")
    Optional<Question> findByIdWithUser(Long questionId);
}
