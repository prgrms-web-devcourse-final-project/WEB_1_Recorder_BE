package com.revup.auth.repository;

import com.revup.auth.dto.token.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken token);

    RefreshToken remove(Long userId);
}
