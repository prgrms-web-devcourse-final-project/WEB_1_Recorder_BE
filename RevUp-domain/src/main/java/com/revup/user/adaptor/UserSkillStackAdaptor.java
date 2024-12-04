package com.revup.user.adaptor;

import com.revup.annotation.Adaptor;
import com.revup.common.SkillStack;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.entity.User;
import com.revup.user.entity.UserSkillStack;
import com.revup.user.repository.UserSkillStackRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class UserSkillStackAdaptor {

    private final UserSkillStackRepository userSkillStackRepository;

    public List<UserSkillStack> findAllByUser(User user) {
        return userSkillStackRepository.findAllByUser(user);
    }

    public void save(UserSkillStack userSkillStacks) {
        userSkillStackRepository.save(userSkillStacks);
    }

    public UserSkillStack findByUserAndStack(User user, SkillStack stack) {
        return userSkillStackRepository.findByUserAndStack(user, stack)
                .orElse(null);
    }

    public UserSkillStack findById(Long id) {
        return userSkillStackRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_SKILL_STACK_NOT_FOUND));
    }

    public void deleteById(Long id) {
        userSkillStackRepository.deleteById(id);
    }
}
