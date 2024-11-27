package com.revup.user.model.request;

import com.revup.user.entity.Profile;
import jakarta.validation.constraints.NotEmpty;

public record UpdateProfileRequest(
        @NotEmpty(message = "닉네임은 필수 입력값입니다.")
        String nickname,
        String profileImage,

        String introduction,

        boolean includeResponse
) {

        public Profile toProfile() {
                return Profile.builder()
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .introduction(introduction)
                        .build();
        }
}
