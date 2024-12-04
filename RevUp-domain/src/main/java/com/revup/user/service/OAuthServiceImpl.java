package com.revup.user.service;

import com.revup.error.AppException;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final UserAdaptor userAdaptor;

    @Override
    @Transactional
    public User loginOrSignup(User user) {
        User loginUser;
        try {
            //소셜 서비스에서 받아온 정보에는 id값이 없음.
            loginUser = userAdaptor.findByTokenClaim(
                    user.getSocialId(),
                    user.getLoginType()
            );
        } catch (AppException e) {
            loginUser = userAdaptor.save(user);
        }

        return loginUser;
    }
}
