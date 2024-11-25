package com.revup.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revup.constants.SecurityConstants;
import com.revup.error.AppException;
import com.revup.exception.UnsupportedTokenException;
import com.revup.jwt.RevUpJwtProvider;
import com.revup.auth.dto.token.TokenInfo;
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


        switch (requestUrl) {
            case REFRESH_URL -> handleRefreshUrl(request, requestMethod);
            case LOGOUT_URL -> handleLogoutUrl(requestMethod);
            default -> handleOthersUrl(request);
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
        String tokenValue = extractToken(request, SecurityConstants.AUTHORIZATION_REFRESH_HEADER);
        if(tokenValue == null) {
            return;
        }

        String tokenType = jwtProvider.getTokenType(tokenValue);

        if(!tokenType.equals("REFRESH")) throw UnsupportedTokenException.EXCEPTION;
        if(!requestMethod.equals(HttpMethod.POST)) {
            throw new AppException(REQUEST_INVALID);
        }

        saveSecurityUserInfo(tokenValue);
    }

    private void handleOthersUrl(HttpServletRequest request) throws JsonProcessingException {
        String tokenValue = extractToken(request, SecurityConstants.AUTHORIZATION_HEADER);
        if(tokenValue == null) {
            return;
        }

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

    // 헤더에서 토큰 추출
    private String extractToken(HttpServletRequest httpServletRequest, String headerKey) {
        String bearerToken = httpServletRequest.getHeader(headerKey);

        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(SecurityConstants.BEARER) &&
                bearerToken.length() > SecurityConstants.BEARER.length()
        ) {
            return bearerToken.substring(SecurityConstants.BEARER.length());
        }

        return null;
    }

    private void setSecurityContextHolder(
            TokenInfo userPrincipal) {
        //권한 없이 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
