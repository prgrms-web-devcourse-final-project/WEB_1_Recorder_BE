package com.revup.utils;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.exception.UserPermissionException;
import com.revup.user.util.UserUtil;
import com.revup.user.entity.User;
import com.revup.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityUserUtil implements UserUtil {

    private final UserReader userReader;

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

    @Override
    public User getCurrentUser() {
        return userReader.findById(getPrincipal().id());
    }

    @Override
    public void checkPermission(User user) {
        if (!user.equals(getCurrentUser())) {
            throw new UserPermissionException();
        }
    }
}
