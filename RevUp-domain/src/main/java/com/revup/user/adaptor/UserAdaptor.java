package com.revup.user.adaptor;

import com.revup.user.entity.User;
import com.revup.user.exception.UserIdNotFoundException;
import com.revup.user.exception.UserPermissionException;
import com.revup.user.repository.UserRepository;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdaptor {
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException(userId));
    }

    public void checkPermission(User user){
        if (!user.equals(userUtil.getCurrentUser())) {
            throw new UserPermissionException();
        }
    }

}
