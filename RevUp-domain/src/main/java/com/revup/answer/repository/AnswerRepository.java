package com.revup.answer.repository;

import com.revup.answer.entity.Answer;
import com.revup.user.entity.User;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, CustomAnswerRepository {

    @OrderBy()
    List<Answer> findByUser(User user);

}
