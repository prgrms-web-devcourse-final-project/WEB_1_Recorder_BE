package com.revup.auth.controller;


import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.auth.service.LogoutUseCase;
import com.revup.auth.service.RefreshTokenUseCase;
import com.revup.constants.SecurityConstants;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    /**
     * refreshToken으로 토큰 갱신
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refreshToken() {
        RefreshTokenResponse tokenResponse = refreshTokenUseCase.execute();
        return ResponseEntity.noContent()
                .header(SecurityConstants.AUTHORIZATION_HEADER, tokenResponse.accessToken())
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

}
