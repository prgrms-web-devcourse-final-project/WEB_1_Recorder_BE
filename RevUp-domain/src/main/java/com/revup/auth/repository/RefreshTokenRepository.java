package com.revup.auth.repository;

import com.revup.auth.dto.token.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken token, Long id);

    void remove(Long userId);

    RefreshToken findById(Long id);
}
