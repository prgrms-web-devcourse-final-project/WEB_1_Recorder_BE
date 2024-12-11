package com.revup.config;

import com.revup.utils.CookieUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.access-expiration-time}")
    private int accessTime;

    @Value("${jwt.refresh-expiration-time}")
    private int refreshTime;

    @PostConstruct
    public void initializeCookieUtils() {
        CookieUtils.setJwtExpirationTimes(accessTime, refreshTime);
    }
}
