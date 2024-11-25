package com.revup.auth.model.mapper;


import com.revup.annotation.Mapper;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;
import com.revup.auth.model.dto.response.FirstLoginResponse;
import com.revup.auth.model.dto.response.RefreshTokenResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Mapper
@RequiredArgsConstructor
public class AuthMapper {


    public RefreshTokenResponse toRefreshTokenResponse(Tokens tokens) {
        String value = tokens.accessToken().value();
        return new RefreshTokenResponse(value);
    }

    public TokenInfo toTokenInfo(User user) {
        return new TokenInfo(user.getSocialId(), user.getLoginType());
    }

    public FirstLoginResponse toFirstLoginResponse(String nickname) {
        return new FirstLoginResponse(Objects.isNull(nickname));
    }
}
