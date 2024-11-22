package com.revup.user.dto;

import com.revup.user.entity.LoginType;

public record UserDto (
        Long id,
        String nickname,
        String profileImage,
        LoginType loginType,
        String socialId,
        String socialEmail,
        String businessEmail,
        String introduction
) {
}
