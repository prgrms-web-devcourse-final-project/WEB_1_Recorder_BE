package com.revup.heart.repository;

import com.revup.answer.entity.Answer;
import com.revup.heart.entity.Heart;
import com.revup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsHeartByAnswerAndUser(Answer answer, User user);

    @Query("select h from Heart h join fetch h.user where h.id=:id")
    Optional<Heart> findByIdWithUser(Long id);

    @Query("select h from Heart h join fetch h.user join fetch h.answer where h.id=:id")
    Optional<Heart> findByIdWithUserAndAnswer(Long id);

    Optional<Heart> findByAnswerIdAndUserId(Long answerId, Long userId);
}
