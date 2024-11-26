package com.revup.question.repository;

import com.revup.question.entity.Question;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select q from Question q where q.id = :id")
    Optional<Question> findByIdWithOptimisticLock(Long id);

}
