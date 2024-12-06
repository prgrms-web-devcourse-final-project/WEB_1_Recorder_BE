package com.revup.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedirectConfig {

    @Bean
    public FilterRegistrationBean<Filter> redirectFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RedirectFilter());
        registrationBean.addUrlPatterns("/*");  // 모든 HTTP 요청을 HTTPS로 리다이렉트
        return registrationBean;
    }

    @WebFilter("/*")
    public class RedirectFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // HTTP로 들어온 요청을 HTTPS로 리다이렉트
            if (httpRequest.getScheme().equals("http")) {
                String redirectUrl = "https://" + httpRequest.getServerName() + ":" + 8443 + httpRequest.getRequestURI();
                if (httpRequest.getQueryString() != null) {
                    redirectUrl += "?" + httpRequest.getQueryString();  // 쿼리 파라미터가 있으면 추가
                }
                httpResponse.sendRedirect(redirectUrl);  // 리다이렉트 실행
            } else {
                // HTTPS로 들어온 요청은 정상적으로 처리
                chain.doFilter(request, response);
            }

        }

        @Override
        public void destroy() {
        }
    }
}