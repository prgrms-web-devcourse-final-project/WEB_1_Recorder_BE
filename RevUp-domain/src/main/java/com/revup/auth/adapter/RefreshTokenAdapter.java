package com.revup.auth.adapter;

import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenAdapter {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken getRefreshTokenById(Long id) {
        return refreshTokenRepository.remove(id);
    }

    public void remove(Long id) {
        refreshTokenRepository.remove(id);
    }

    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}
