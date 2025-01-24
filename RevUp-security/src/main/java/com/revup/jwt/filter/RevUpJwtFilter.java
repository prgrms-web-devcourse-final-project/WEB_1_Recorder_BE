package com.revup.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revup.constants.SecurityConstants;
import com.revup.error.AppException;
import com.revup.exception.NotFoundTokenException;
import com.revup.exception.UnsupportedTokenException;
import com.revup.jwt.RevUpJwtProvider;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.revup.constants.SecurityConstants.EXCEPTION;
import static com.revup.error.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RevUpJwtFilter extends OncePerRequestFilter {

    private final RevUpJwtProvider jwtProvider;

    private static final String REFRESH_URL = "/api/v1/auth/refresh";
    private static final String LOGOUT_URL = "/api/v1/auth/logout";


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUrl = request.getServletPath();
        log.info("requestUrl = {}", requestUrl);

        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());

        try {
            switch (requestUrl) {
                case REFRESH_URL -> handleRefreshUrl(request, requestMethod);
                case LOGOUT_URL -> handleLogoutUrl(requestMethod);
                default -> handleOthersUrl(request);
            }
        } catch (AppException e) {
            request.setAttribute(EXCEPTION, e);
        }
        log.debug("다음으로 이동");
        filterChain.doFilter(request, response);
    }

    //refreshToken을 지우기 위한 목적으로 메서드만 맞으면 통과
    private void handleLogoutUrl(HttpMethod requestMethod) {
        if(!requestMethod.equals(HttpMethod.GET)) {
            throw new AppException(REQUEST_INVALID);
        }
    }

    private void handleRefreshUrl(HttpServletRequest request, HttpMethod requestMethod) throws JsonProcessingException {
        String tokenValue = extractRefreshTokenFromCookie(request);
        log.info("filter.refreshToken = {}", tokenValue);

        String tokenType = jwtProvider.getTokenType(tokenValue);

        if(!tokenType.equals("REFRESH")) throw UnsupportedTokenException.EXCEPTION;
        if(!requestMethod.equals(HttpMethod.POST)) {

            throw new AppException(REQUEST_INVALID);
        }

        saveSecurityUserInfo(tokenValue);
    }

    private void handleOthersUrl(HttpServletRequest request) throws JsonProcessingException {
//        String tokenValue = extractAccessTokenFromHeader(request);
        String tokenValue = extractAccessTokenFromCookie(request);
        log.info("filter.accessToken = {}", tokenValue);

        String tokenType = jwtProvider.getTokenType(tokenValue);
        if(!tokenType.equals("ACCESS")) throw UnsupportedTokenException.EXCEPTION;
        saveSecurityUserInfo(tokenValue);
    }

    private TokenInfo extractTokenInfo(String token) {
        log.debug("token = {}", token);
        TokenInfo tokenUserPrincipal = jwtProvider.getTokenUserPrincipal(token);
        log.info("tokenUserPrincipal = {}", tokenUserPrincipal);
        return tokenUserPrincipal;
    }

    private void saveSecurityUserInfo(String tokenValue) {
        TokenInfo tokenInfo = extractTokenInfo(tokenValue);
        setSecurityContextHolder(tokenInfo);
    }

    // accessToken토큰 추출
    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(SecurityConstants.BEARER) &&
                bearerToken.length() > SecurityConstants.BEARER.length()
        ) {
            return bearerToken.substring(SecurityConstants.BEARER.length());
        } else {
            return CookieUtils.getCookie(request, SecurityConstants.AUTHORIZATION_HEADER)
                    .orElseThrow(() -> NotFoundTokenException.EXCEPTION).getValue();
        }
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        return CookieUtils.getCookie(request, SecurityConstants.AUTHORIZATION_HEADER)
                .orElseThrow(() -> NotFoundTokenException.EXCEPTION).getValue();
    }

    private String extractRefreshTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.AUTHORIZATION_REFRESH_HEADER);
        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(SecurityConstants.BEARER) &&
                bearerToken.length() > SecurityConstants.BEARER.length()
        ) {
            return bearerToken.substring(SecurityConstants.BEARER.length());
        } else {
            return CookieUtils.getCookie(
                    request, SecurityConstants.AUTHORIZATION_REFRESH_HEADER
                    )
                    .orElseThrow(() -> NotFoundTokenException.EXCEPTION).getValue();
        }
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        return CookieUtils.getCookie(request, SecurityConstants.AUTHORIZATION_REFRESH_HEADER)
                .orElseThrow(() -> NotFoundTokenException.EXCEPTION).getValue();
    }

    private void setSecurityContextHolder(
            TokenInfo userPrincipal) {
        //권한 없이 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
