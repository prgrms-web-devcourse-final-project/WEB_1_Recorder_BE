package com.revup.auth.model.mapper;


import com.revup.annotation.Mapper;
import com.revup.auth.dto.token.Tokens;
import com.revup.auth.model.dto.response.FirstLoginResponse;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class AuthMapper {


    public RefreshTokenResponse toRefreshTokenResponse(Tokens tokens) {
        return new RefreshTokenResponse(
                tokens.accessToken().value(),
                tokens.refreshToken().value()
        );
    }

    public FirstLoginResponse toFirstLoginResponse(User user) {
        return new FirstLoginResponse(user.isFirst());
    }
}
