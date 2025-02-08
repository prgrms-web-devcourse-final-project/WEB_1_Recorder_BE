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
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(
                "https://revup-eight.vercel.app",
                "https://revuprevup.o-r.kr",
                githubPageUrl,
                "http://localhost:3000"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 요청 헤더 설정
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Origin",
                "Cookie"
        ));

        // 필요한 헤더만 노출
        configuration.setExposedHeaders(List.of(
                "Set-Cookie"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}