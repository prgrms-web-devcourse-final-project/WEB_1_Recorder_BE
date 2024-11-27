package com.revup.user.adaptor;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.entity.LoginType;
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

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByTokenClaim(String socialId, LoginType loginType) {
        return userRepository.findBySocialIdAndLoginType(socialId, loginType)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public void checkPermission(User user){
        if (!user.equals(userUtil.getCurrentUser())) {
            throw new UserPermissionException();
        }
    }
}
