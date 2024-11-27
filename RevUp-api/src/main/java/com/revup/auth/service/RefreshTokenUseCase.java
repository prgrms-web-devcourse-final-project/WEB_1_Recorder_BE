package com.revup.auth.service;

import com.revup.annotation.UseCase;
import com.revup.auth.adapter.RefreshTokenAdapter;
import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.auth.model.mapper.AuthMapper;
import com.revup.jwt.RevUpJwtProvider;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final UserUtil userUtil;
    private final TokenGenerator jwtGenerator;
    private final RevUpJwtProvider jwtProvider;
    private final TokenValidator jwtValidator;
    private final RefreshTokenAdapter refreshTokenAdapter;
    private final AuthMapper authMapper;

    /**
     * 1. 사용자 정보를 가져옴
     * 2. token 저장소에 저장된 refreshToken 조회
     * 3. 1과 2에서 받아온 사용자의 정보를 통해 valid토큰의 유효성 검사
     * 3.1 유효
     *      generator에서 토큰 생성
     *      refresh 다시 저장
     *      accessToken & refreshToken 담아서 보냄
     * 3.2 유효x
     *      token 저장소에서 삭제
     *      에러 던짐 - 유효한 토큰 x - 로그아웃 처리 필요(프론트 / 백 공통)
     * --------
     * 로직이 맞는지 잘 모르겠네..
     * @return
     */
    public RefreshTokenResponse execute(String clientToken) {
        TokenInfo principal = userUtil.getPrincipal();
        RefreshToken redisToken = refreshTokenAdapter.findById(principal.id());

        jwtValidator.validateSameToken(clientToken, redisToken.value());
        TokenInfo redisUserInfo = jwtProvider.getTokenUserPrincipal(redisToken.value());

        Tokens newTokens = jwtGenerator.generate(redisUserInfo);
        refreshTokenAdapter.saveRefreshToken(newTokens.refreshToken(), redisUserInfo.id());
        return authMapper.toRefreshTokenResponse(newTokens);
    }
}
