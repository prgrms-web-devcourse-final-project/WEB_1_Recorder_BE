package com.revup.user.service;


import com.revup.auth.dto.token.TokenInfo;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.entity.User;
import com.revup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User findByTokenInfo(TokenInfo tokenInfo) {
        return userRepository.findBySocialIdAndLoginType(
                tokenInfo.socialId(),
                tokenInfo.loginType()
        ).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
