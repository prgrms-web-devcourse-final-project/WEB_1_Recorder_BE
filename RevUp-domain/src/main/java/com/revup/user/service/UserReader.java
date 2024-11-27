package com.revup.user.service;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReader {

    private final UserAdaptor userAdaptor;

    /**
     * 로그인 시 소셜 서비스에 연동된
     * 회원에 대한 정보를 조회하는 메서드
     * @param tokenInfo
     * @return
     */

    public User findByTokenInfo(TokenInfo tokenInfo) {
        return userAdaptor.findByTokenClaim(
                tokenInfo.socialId(),
                tokenInfo.loginType()
        );
    }

    public User findById(Long id) {
        return userAdaptor.findById(id);
    }
}
