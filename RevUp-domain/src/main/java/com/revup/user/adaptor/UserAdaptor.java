package com.revup.user.adaptor;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.repository.UserRepository;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdaptor {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, userId));
    }

}
