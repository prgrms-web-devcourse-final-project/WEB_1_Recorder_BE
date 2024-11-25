package com.revup.user.service;


import com.revup.auth.dto.token.TokenInfo;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.entity.User;
import com.revup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    /**
     * 로그인 시 소셜 서비스에 연동된
     * 회원에 대한 정보를 조회하는 메서드
     * @param tokenInfo
     * @return
     */
    public User findByTokenInfo(TokenInfo tokenInfo) {
        return userRepository.findBySocialIdAndLoginType(
                tokenInfo.socialId(),
                tokenInfo.loginType()
        ).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
