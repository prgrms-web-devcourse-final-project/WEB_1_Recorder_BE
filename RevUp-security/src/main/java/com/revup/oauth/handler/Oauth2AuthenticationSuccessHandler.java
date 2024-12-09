package com.revup.oauth.handler;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;
import com.revup.auth.repository.RefreshTokenRepository;
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
import org.springframework.beans.factory.annotation.Value;
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
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.access-expiration-time}")
    private int accessTime;

    @Value("${jwt.refresh-expiration-time}")
    private int refreshTime;

    //redisToken 넣는 작업 필요.

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = getUser(authentication);
        Tokens tokens = createTokens(user);
        refreshTokenRepository.save(tokens.refreshToken(), user.getId());

        if (response.isCommitted()) {
            return;
        }

        log.info("tokens = {}",  tokens);
        String targetUrl = determineTargetUrl(request, tokens, user);

        setToken(response, tokens);
        log.info("cookies = {}", response.getHeaders("Set-Cookie"));
        
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void setToken(HttpServletResponse response, Tokens tokens) {
        CookieUtils.addCookie(
                response,
                SecurityConstants.AUTHORIZATION_HEADER,
                tokens.accessToken().value(),
                accessTime);

        CookieUtils.addCookie(
                response,
                SecurityConstants.AUTHORIZATION_REFRESH_HEADER,
                tokens.refreshToken().value(),
                refreshTime);
    }

    protected String determineTargetUrl(
            HttpServletRequest request,
            Tokens tokens,
            User user
    ) {
        String targetUrl = CookieUtils.getCookie(
                request,
                REDIRECT_URI_PARAM_COOKIE_NAME
        ).map(Cookie::getValue).orElse(getDefaultTargetUrl());
        log.info("targetUrl = {}", targetUrl);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("is_first", user.isFirst())
                .queryParam("access_token", tokens.accessToken().value())
                .queryParam("refresh_token", tokens.refreshToken().value())
                .build().toUriString();
    }

    private Tokens createTokens(User loginUser) {
        return jwtGenerator.generate(
                new TokenInfo(
                        loginUser.getId(),
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

    protected void clearAuthenticationAttributes(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        super.clearAuthenticationAttributes(request);
        httpCookieOauth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
