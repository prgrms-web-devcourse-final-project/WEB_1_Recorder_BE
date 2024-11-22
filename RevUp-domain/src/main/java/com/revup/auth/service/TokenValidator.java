package com.revup.auth.service;

import com.revup.auth.dto.token.AccessToken;
import com.revup.auth.dto.token.RefreshToken;

public interface TokenValidator {

    void validate(AccessToken token);

    void validate(RefreshToken token);
}
