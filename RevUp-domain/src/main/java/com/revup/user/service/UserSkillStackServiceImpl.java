package com.revup.user.service;

import com.revup.common.SkillStack;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.adaptor.UserSkillStackAdaptor;
import com.revup.user.entity.User;
import com.revup.user.entity.UserSkillStack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserSkillStackServiceImpl implements UserSkillStackService {

    private final UserSkillStackAdaptor userSkillStackAdaptor;

    @Override
    @Transactional(readOnly = true)
    public List<UserSkillStack> findByUser(User user) {
        return userSkillStackAdaptor.findAllByUser(user);
    }

    @Override
    public List<UserSkillStack> updateSkillStack(User user, SkillStack skillStack) {
        UserSkillStack findUserStack = userSkillStackAdaptor.findByUserAndStack(user, skillStack);
        validateDuplicate(findUserStack);
        UserSkillStack newSkillStack = UserSkillStack.of(user, skillStack);
        userSkillStackAdaptor.save(newSkillStack);
        return userSkillStackAdaptor.findAllByUser(user);
    }

    @Override
    public List<UserSkillStack> deleteMySkillStack(User user, Long id) {
        UserSkillStack findSkillStack = userSkillStackAdaptor.findById(id);
        if(!user.equals(findSkillStack.getUser())) {
            throw new AppException(ErrorCode.NOT_MY_SKILL_STACK);
        }
        log.info("findSkillStack = {}", findSkillStack);
        userSkillStackAdaptor.deleteById(id);
        return userSkillStackAdaptor.findAllByUser(user);
    }

    private void validateDuplicate(UserSkillStack userSkillStack) {
        if(userSkillStack != null) {
            throw new AppException(ErrorCode.DUPLICATED_USER_SKILL_STACK);
        }
    }

}
