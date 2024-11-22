package com.revup.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revup.auth.dto.LoginInfo;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;
import com.revup.constants.SecurityConstants;
import com.revup.jwt.RevUpJwtGenerator;
import com.revup.oauth.repository.HttpCookieOauth2AuthorizationRequestRepository;
import com.revup.oauth.service.UserCreator;
import com.revup.user.entity.User;
import com.revup.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.revup.oauth.repository.HttpCookieOauth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Slf4j
@RequiredArgsConstructor
@Component
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOauth2AuthorizationRequestRepository httpCookieOauth2AuthorizationRequestRepository;
    private final UserCreator userCreator;
    private final RevUpJwtGenerator jwtGenerator;
    //redisToken 넣는 작업 필요.

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = getUser(authentication);
        Tokens tokens = createTokens(user);

        if (response.isCommitted()) {
            return;
        }

        String targetUrl = determineTargetUrl(request, tokens);

        // 쿠키 설정 (예: accessToken을 쿠키로 설정)
        setCookie(response, tokens);
        setResponse(response, tokens, user);

//        response.sendRedirect(targetUrl);
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(
            HttpServletRequest request,
            Tokens tokens
    ) {
        String targetUrl = CookieUtils.getCookie(
                request,
                REDIRECT_URI_PARAM_COOKIE_NAME
        ).map(Cookie::getValue).orElse(getDefaultTargetUrl());
        log.info("targetUrl = {}", targetUrl);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", tokens.accessToken().value())
                .queryParam("refresh_token", tokens.refreshToken().value())
                .build().toUriString();
    }

    private Tokens createTokens(User loginUser) {
        return jwtGenerator.generate(
                new TokenInfo(
                        loginUser.getSocialId(),
                        loginUser.getLoginType()
                )
        );
    }

    private User getUser(Authentication authentication) {
        User loginUser = userCreator.create(authentication);
        log.info("getUser().loginUser = {}", loginUser);
        return loginUser;
    }

    // 쿠키 설정
    private void setCookie(HttpServletResponse response, Tokens tokens) {
        Cookie accessTokenCookie = new Cookie(
                SecurityConstants.AUTHORIZATION_HEADER, tokens.accessToken().value()
        );
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(3600); // 1시간
        accessTokenCookie.setPath("/"); // 전체 경로에서 사용 가능


        response.addCookie(accessTokenCookie);
    }

    private static void setResponse(
            HttpServletResponse response,
            Tokens tokens,
            User loginUser
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(SecurityConstants.AUTHORIZATION_HEADER, tokens.accessToken().value());

        LoginInfo loginInfo = new LoginInfo(loginUser.getNickname() == null);
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginInfo));
    }

    protected void clearAuthenticationAttributes(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        super.clearAuthenticationAttributes(request);
        httpCookieOauth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
