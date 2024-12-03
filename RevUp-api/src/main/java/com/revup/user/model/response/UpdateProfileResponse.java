package com.revup.user.model.response;

import com.revup.user.entity.Profile;

public record UpdateProfileResponse(
        String nickname,
        String profileImage,
        String introduction
) {
    public static UpdateProfileResponse of(Profile profile) {
        return new UpdateProfileResponse(
                profile.getNickname(),
                profile.getProfileImage(),
                profile.getIntroduction()
        );
    }
}
