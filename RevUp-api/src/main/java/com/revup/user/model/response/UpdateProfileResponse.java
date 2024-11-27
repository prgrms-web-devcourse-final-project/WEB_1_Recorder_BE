package com.revup.user.model.response;

public record UpdateProfileResponse(
        String nickname,
        String profileImage,
        String businessEmail
) {
}
