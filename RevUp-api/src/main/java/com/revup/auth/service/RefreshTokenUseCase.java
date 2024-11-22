package com.revup.auth.service;

import com.revup.auth.adapter.RefreshTokenAdapter;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.jwt.RevUpJwtGenerator;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final RevUpJwtGenerator jwtGenerator;
    private final UserUtil userUtil;
    private final RefreshTokenAdapter refreshTokenAdapter;

    public RefreshTokenResponse refresh() {
        User currentUser = userUtil.getCurrentUser();

//        refreshTokenAdapter.
        return new RefreshTokenResponse(currentUser.toString());
    }
}
