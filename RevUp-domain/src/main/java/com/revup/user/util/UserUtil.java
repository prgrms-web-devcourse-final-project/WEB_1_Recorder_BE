package com.revup.user.util;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.entity.User;

public interface UserUtil {

    TokenInfo getPrincipal();

    User getCurrentUser();
}
