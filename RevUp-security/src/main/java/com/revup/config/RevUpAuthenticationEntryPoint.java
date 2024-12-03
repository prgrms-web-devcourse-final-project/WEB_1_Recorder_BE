package com.revup.config;

import com.revup.error.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;


import static com.revup.constants.SecurityConstants.EXCEPTION;
import static com.revup.error.ErrorCode.TOKEN_TIMEOUT;

@Slf4j
@Component
public class RevUpAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public RevUpAuthenticationEntryPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) {
        log.error("authException.getCause = {}", authException.getCause());
        log.info("JwtAuthenticationEntryPoint JwtAuthenticationEntryPoint");
        if (isExceptionInSecurityFilter(request, response)) {
            resolver.resolveException(
                    request, response, null, (Exception) request.getAttribute("exception"));
            return;
        }
        resolver.resolveException(request, response, null, new AppException(TOKEN_TIMEOUT));
    }

    private boolean isExceptionInSecurityFilter(HttpServletRequest request, HttpServletResponse response) {
//        log.info("Response = {}", response);
        return request.getAttribute(EXCEPTION) != null;
    }
}
