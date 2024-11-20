package com.revup.question.repository;

import com.revup.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository {
    @Query("select q from Question q left join fetch q.user where q.id = :id")
    Optional<Question> findByIdWithUser(Long id);

}
