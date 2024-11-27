package com.revup.auth.controller;


import com.revup.auth.model.dto.response.FirstLoginResponse;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.auth.service.LoginUseCase;
import com.revup.auth.service.LogoutUseCase;
import com.revup.auth.service.RefreshTokenUseCase;
import com.revup.constants.SecurityConstants;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final LoginUseCase loginUseCase;

    /**
     * refreshToken으로 토큰 갱신
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refreshToken(
            @RequestHeader(name = SecurityConstants.AUTHORIZATION_REFRESH_HEADER) String refreshToken
    ) {
        RefreshTokenResponse tokenResponse = refreshTokenUseCase.execute(refreshToken);
        return ResponseEntity.noContent()
                .header(SecurityConstants.AUTHORIZATION_HEADER, tokenResponse.accessToken())
                .header(SecurityConstants.AUTHORIZATION_REFRESH_HEADER, tokenResponse.refreshToken())
                .build();
    }

    /**
     * 로그아웃
     * 만약 로그아웃을 했는데 토큰을 가지고 요청을 보내는 경우가 생긴다면?
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        logoutUseCase.execute();
        return ResponseEntity.noContent().build();
    }

    /**
     * 리다이렉트 페이지에서
     * 사용자의 최초 로그인 여부를 반환해주는 api
     * (토큰 필수)
     * @return
     */
    @GetMapping("/first-login")
    public ResponseEntity<ApiResponse<FirstLoginResponse>> checkFirstLogin() {
        FirstLoginResponse response = loginUseCase.execute();
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }
}
