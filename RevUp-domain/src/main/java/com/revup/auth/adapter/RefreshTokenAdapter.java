package com.revup.auth.adapter;

import com.revup.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenAdapter {

    private final RefreshTokenRepository refreshTokenRepository;
}
