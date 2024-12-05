package com.revup.user.repository;


import com.revup.user.entity.LoginType;
import com.revup.user.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findBySocialIdAndLoginType(String socialId, LoginType loginType);

    @Modifying
    @Query(value = "UPDATE users u " +
            "SET u.total_answer_count = u.total_answer_count - " +
            "(SELECT COUNT(*) FROM answer a WHERE a.question_id = :questionId AND a.writer_id = u.id) " +
            "WHERE u.id IN (SELECT a.writer_id FROM answer a WHERE a.question_id = :questionId)",
            nativeQuery = true)
    void decrementTotalAnswersByQuestionId( Long questionId);

    @Modifying
    @Query("update User u set u.profile.adoptedAnswerCount = u.profile.adoptedAnswerCount - 1 where u.id in " +
            "(select a.user.id from Answer a where a.question.id = :questionId and a.isAccept = 'TRUE')")
    void decrementAcceptedAnswersByQuestionId(Long questionId);
}
