package com.revup.user.repository;

import com.revup.common.SkillStack;
import com.revup.user.entity.User;
import com.revup.user.entity.UserSkillStack;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillStackRepository extends CrudRepository<UserSkillStack, Long> {

    List<UserSkillStack> findAllByUser(User user);

    Optional<UserSkillStack> findByUserAndStack(User user, SkillStack stack);
}
