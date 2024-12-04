package com.revup.user.service;

import com.revup.common.SkillStack;
import com.revup.user.entity.User;
import com.revup.user.entity.UserSkillStack;

import java.util.List;

public interface UserSkillStackService {

    List<UserSkillStack> findByUser(User user);

    List<UserSkillStack> updateSkillStack(User user, SkillStack skillStack);

    List<UserSkillStack> deleteMySkillStack(User user, Long id);
}
