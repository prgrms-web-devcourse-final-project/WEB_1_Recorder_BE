package com.revup.config;

import com.revup.handler.SecurityUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SecurityUserArgumentResolver securityUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(securityUserArgumentResolver);
    }

    //백엔트 토큰 발급 사이트
    @Value("${github-pages.url}")
    private String githubPageUrl;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "https://revup-eight.vercel.app",
                githubPageUrl,
                "http://localhost:3000"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 허용할 요청 헤더 설정
        configuration.setAllowedHeaders(List.of(
                "Authorization",  // 인증 토큰
                "Content-Type",   // 요청 본문의 콘텐츠 타입
                "Accept",         // 클라이언트가 기대하는 응답 타입
                "X-Requested-With",
                "Cookie",          // 쿠키 정보 (필요한 경우)
                "Access-Control-Allow-Credentials"
        ));
        configuration.setAllowCredentials(true);

        // 필요한 헤더만 노출
        configuration.setExposedHeaders(List.of(
                "Authorization-refresh",
                "Authorization",
                "Set-Cookie"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
