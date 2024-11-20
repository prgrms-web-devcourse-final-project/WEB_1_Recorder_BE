package com.revup.question.repository;

import com.revup.question.entity.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, Long> {

}
