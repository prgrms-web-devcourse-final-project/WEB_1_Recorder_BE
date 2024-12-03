package com.revup.feedback.repository;

import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.enums.FeedbackState;
import com.revup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>, CustomFeedBackRepository {

    List<Feedback> findByTeacherAndState(User user, FeedbackState state);

    List<Feedback> findByTeacher(User user);

}
