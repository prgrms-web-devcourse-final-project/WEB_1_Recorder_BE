package com.revup.auth.repository;

import com.revup.auth.dto.token.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken token, Long id);

    void remove(Long userId);

    Optional<RefreshToken> findById(Long id);
}
