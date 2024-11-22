package com.revup.jwt.filter;

import com.revup.auth.dto.token.AccessToken;
import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.service.TokenValidator;
import com.revup.constants.SecurityConstants;
import com.revup.error.AppException;
import com.revup.error.SecurityException;
import com.revup.exception.UnsupportedTokenException;
import com.revup.jwt.RevUpJwtProvider;
import com.revup.auth.dto.token.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.revup.error.ErrorCode.TOKEN_NOT_EXIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class RevUpJwtFilter extends OncePerRequestFilter {

    private final RevUpJwtProvider jwtProvider;
    private final TokenValidator jwtValidator;

    private final String REFRESH_URL = "/api/v1/auth/refresh";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String requestUrl = request.getServletPath();
            log.info("requestUrl = {}", requestUrl);
            if("/favicon.ico".equals(requestUrl)) return;


            String tokenValue = extractToken(request);
            String tokenType = jwtProvider.getTokenType(tokenValue);
            switch (requestUrl) {
                case REFRESH_URL -> handleRefreshUrl(new RefreshToken(tokenValue), tokenType);
                default -> handleOthersUrl(new AccessToken(tokenValue), tokenType);
            }

        } catch (SecurityException e) {
            log.error("Application Exception: {}", e.getErrorCode(), e);
            setErrorResponse(
                    response,
                    e.getErrorCode().getHttpStatus(),
                    e.getErrorCode().getMessageTemplate()
            );
            return;
        } catch (Exception e) {
            log.error("Unexpected Exception: {}", e.getMessage(), e);
            setErrorResponse(
                    response,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal Server Error"
            );
            return;
        }

        log.debug("다음으로 이동");
        filterChain.doFilter(request, response);
    }

    private void handleRefreshUrl(RefreshToken token, String tokenType) {
        TokenInfo tokenInfo = extractTokenInfo(token.value());
        setSecurityContextHolder(tokenInfo);
    }

    private void handleOthersUrl(AccessToken token, String tokenType) {
        if(!tokenType.equals("ACCESS")) throw UnsupportedTokenException.EXCEPTION;
        jwtValidator.validate(token);
        TokenInfo tokenInfo = extractTokenInfo(token.value());
        setSecurityContextHolder(tokenInfo);
    }

    private TokenInfo extractTokenInfo(String token) {
        log.debug("token = {}", token);
        TokenInfo tokenUserPrincipal = jwtProvider.getTokenUserPrincipal(token);
        log.info("tokenUserPrincipal = {}", tokenUserPrincipal);
        return tokenUserPrincipal;
    }

    // 헤더에서 토큰 추출
    private String extractToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.BEARER)) {
            if (bearerToken.length() > SecurityConstants.BEARER.length()) {
                return bearerToken.substring(SecurityConstants.BEARER.length());
            }
        }

        // 토큰 정보 칸이 비어있으면 없는 토큰으로 간주하고 오류 발생
        throw new AppException(TOKEN_NOT_EXIST);
    }

    private void setSecurityContextHolder(
            TokenInfo userPrincipal) {
        //권한 없이 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
        } catch (IOException ioException) {
            log.error("Error writing response: {}", ioException.getMessage());
        }
    }
}
