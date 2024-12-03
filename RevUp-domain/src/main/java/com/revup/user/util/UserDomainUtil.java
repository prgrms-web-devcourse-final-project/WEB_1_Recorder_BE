package com.revup.user.util;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.UserUtil;
import com.revup.user.entity.User;

public interface UserDomainUtil extends UserUtil {

    TokenInfo getPrincipal();
}
