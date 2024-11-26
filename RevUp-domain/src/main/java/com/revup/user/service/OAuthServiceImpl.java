package com.revup.user.service;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.error.AppException;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final UserRegistrar userRegistrar;
    private final UserReader userReader;

    @Override
    public User loginOrSignup(User user) {
        log.info("{}, {}", user.getSocialId(), user.getLoginType());
        User loginUser;
        try {
            loginUser = userReader.findByTokenInfo(
                    new TokenInfo(user.getId(), user.getSocialId(), user.getLoginType())
            );
        } catch (AppException e) {
            loginUser = userRegistrar.register(user);
        }

        return loginUser;
    }
}
