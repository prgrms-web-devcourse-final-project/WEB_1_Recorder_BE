package com.revup.feedback.repository;

import com.revup.feedback.entity.Mentor;

import java.util.List;

public interface CustomMentorRepository {

    List<Mentor> findAllWithMentorSkillStacksAndUser();

}
