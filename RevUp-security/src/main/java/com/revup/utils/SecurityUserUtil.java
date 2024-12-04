package com.revup.utils;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.util.UserDomainUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityUserUtil implements UserDomainUtil {

    @Override
    public TokenInfo getPrincipal() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        log.info("principal = {}", principal);
        return (TokenInfo) principal;
    }

    @Override
    public Long getSubject() {
        return getPrincipal().id();
    }

}
