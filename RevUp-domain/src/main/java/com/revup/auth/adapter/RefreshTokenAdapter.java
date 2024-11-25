package com.revup.auth.adapter;

import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.repository.RefreshTokenRepository;
import com.revup.error.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.revup.error.ErrorCode.REFRESH_TOKEN_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RefreshTokenAdapter {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findById(Long id) {
        return refreshTokenRepository.findById(id)
                .orElseThrow(() -> new SecurityException(REFRESH_TOKEN_NOT_FOUND));
    }

    public void remove(Long id) {
        refreshTokenRepository.remove(id);
    }

    public void saveRefreshToken(RefreshToken refreshToken, Long id) {
        refreshTokenRepository.save(refreshToken, id);
    }
}
