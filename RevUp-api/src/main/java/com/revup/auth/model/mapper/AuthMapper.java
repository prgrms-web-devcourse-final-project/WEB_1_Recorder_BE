package com.revup.auth.model.mapper;


import com.revup.annotation.Mapper;
import com.revup.auth.dto.token.Tokens;
import com.revup.auth.model.dto.response.FirstLoginResponse;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.user.entity.Profile;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Mapper
@RequiredArgsConstructor
public class AuthMapper {


    public RefreshTokenResponse toRefreshTokenResponse(Tokens tokens) {
        return new RefreshTokenResponse(
                tokens.accessToken().value(),
                tokens.refreshToken().value()
        );
    }

    public FirstLoginResponse toFirstLoginResponse(Profile profile) {
        return new FirstLoginResponse(Objects.isNull(profile));
    }
}
