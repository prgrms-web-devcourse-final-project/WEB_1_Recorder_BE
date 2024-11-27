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
        User loginUser;
        try {
            //소셜 서비스에서 받아온 정보에는 id값이 없음.
            loginUser = userReader.findByTokenInfo(
                    new TokenInfo(user.getId(), user.getSocialId(), user.getLoginType())
            );
        } catch (AppException e) {
            loginUser = userRegistrar.register(user);
        }

        return loginUser;
    }
}
