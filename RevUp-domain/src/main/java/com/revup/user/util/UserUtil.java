package com.revup.user.util;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.entity.User;

public interface UserUtil {

    TokenInfo getPrincipal();

    Long getSubject();

    User getCurrentUser();

    void checkPermission(User user);
}
