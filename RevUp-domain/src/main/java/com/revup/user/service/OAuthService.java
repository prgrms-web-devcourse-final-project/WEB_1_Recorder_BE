package com.revup.user.service;

import com.revup.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface OAuthService {

    User loginOrSignup(User loginUser);
}
