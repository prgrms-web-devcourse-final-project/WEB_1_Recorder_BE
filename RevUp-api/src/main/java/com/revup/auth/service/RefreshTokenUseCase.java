package com.revup.auth.service;

import com.revup.auth.adapter.RefreshTokenAdapter;
import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.auth.model.mapper.AuthMapper;
import com.revup.jwt.RevUpJwtGenerator;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final UserUtil userUtil;
    private final RevUpJwtGenerator jwtGenerator;
    private final TokenValidator jwtValidator;
    private final RefreshTokenAdapter refreshTokenAdapter;
    private final AuthMapper authMapper;

    /**
     * 1. 사용자 정보를 가져옴
     * 2. token 저장소에서 발급
     * 3. valid토큰의 유효성 검사
     * 3.1 유효
     *      generator에서 토큰 생성
     *      refresh 다시 저장
     *      accessToken 담아서 보냄
     * 3.2 유효x
     *      token 저장소에서 삭제
     *      에러 던짐 - 유효한 토큰 x - 로그아웃 처리 필요(프론트 / 백 공통)
     * --------
     * 아니면 삭제하면서 값을 받는 방식은 없나?
     * @return
     */
    public RefreshTokenResponse execute() {
        User currentUser = userUtil.getCurrentUser();
        Long userId = currentUser.getId();

        RefreshToken refreshToken = refreshTokenAdapter.getRefreshTokenById(userId);
        jwtValidator.validate(refreshToken);

        TokenInfo tokenInfo = authMapper.toTokenInfo(currentUser);
        Tokens newTokens = jwtGenerator.generate(tokenInfo, userId);
        refreshTokenAdapter.saveRefreshToken(newTokens.refreshToken());

        return authMapper.toRefreshTokenResponse(newTokens);
    }
}
