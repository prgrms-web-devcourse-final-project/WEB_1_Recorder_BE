package com.revup.heart.repository;

import com.revup.answer.entity.Answer;
import com.revup.heart.entity.Heart;
import com.revup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsHeartByAnswerAndUser(Answer answer, User user);

}
