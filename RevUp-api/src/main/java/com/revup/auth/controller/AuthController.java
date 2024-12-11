package com.revup.auth.controller;


import com.revup.annotation.SecurityUser;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.model.dto.request.TokenRequest;
import com.revup.auth.model.dto.response.FirstLoginResponse;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.auth.service.AuthenticationUseCase;
import com.revup.constants.SecurityConstants;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import com.revup.user.util.UserDomainUtil;
import com.revup.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationUseCase authenticationUseCase;
    private final UserDomainUtil userDomainUtil;
    @Value("${jwt.access-expiration-time}")
    private int accessTime;

    @Value("${jwt.refresh-expiration-time}")
    private int refreshTime;

    /**
     * refreshToken으로 토큰 갱신
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refreshToken(
            @RequestHeader(name = SecurityConstants.AUTHORIZATION_REFRESH_HEADER, required = false) String refreshToken
    ) {
        TokenInfo info = userDomainUtil.getPrincipal();
        RefreshTokenResponse tokenResponse = authenticationUseCase.executeRefresh(info, refreshToken);

//        saveToken(response, tokenResponse.accessToken(), tokenResponse.refreshToken());

        // ResponseEntity 생성
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
        Long subject = userDomainUtil.getSubject();
        authenticationUseCase.executeLogout(subject);
        return ResponseEntity.noContent().build();
    }

    /**
     * 리다이렉트 페이지에서
     * 사용자의 최초 로그인 여부를 반환해주는 api
     * (토큰 필수)
     * @return
     */
    @PostMapping("/first-login")
    public ResponseEntity<ApiResponse<FirstLoginResponse>> checkFirstLogin(
            @RequestBody TokenRequest request,
            @SecurityUser User user,
            HttpServletResponse response
    ) {
        FirstLoginResponse res = authenticationUseCase.executeLogin(user);
//        saveToken(response, request.accessToken(), request.refreshToken());
        return ResponseEntity.ok().body(ApiResponse.success(res));
    }

    private void saveToken(
            HttpServletResponse response,
            String accessToken,
            String refreshToken
    ) {
        // 쿠키 생성 및 추가
        CookieUtils.addCookie(
                response,
                SecurityConstants.AUTHORIZATION_REFRESH_HEADER,
                refreshToken,
                refreshTime
        );

        CookieUtils.addCookie(
                response,
                SecurityConstants.AUTHORIZATION_HEADER,
                accessToken,
                accessTime
        );
    }
}
