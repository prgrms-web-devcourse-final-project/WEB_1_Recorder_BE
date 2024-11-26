package com.revup.user.service;

import com.revup.user.entity.User;

public interface OAuthService {

    User loginOrSignup(User loginUser);
}
