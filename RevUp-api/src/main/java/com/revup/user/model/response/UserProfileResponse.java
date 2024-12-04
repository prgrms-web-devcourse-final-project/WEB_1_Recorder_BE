package com.revup.user.model.response;

import com.revup.user.entity.Affiliation;
import com.revup.user.entity.User;

public record UserProfileResponse(

        String nickname,
        String profileImage,
        String introduction,
        String businessEmail,
        String affiliationName,
        String loginType,
        String socialId,
        int totalAnswerCount,
        int adoptedAnswerCount
) {
    public static UserProfileResponse of(User user) {
        Affiliation affiliation = user.getAffiliation();

        String introduction = user.getProfile().isEmptyIntroduction() ?
                "소개글이 없습니다." :
                user.getProfile().getIntroduction();

        return new UserProfileResponse(
                user.getNickname(),
                user.getProfileImage(),
                introduction,
                affiliation == null ? "소속없음" : affiliation.getBusinessEmail(),
                affiliation == null? "연동한 소속 이메일이 없습니다." : affiliation.getAffiliationName(),
                user.getLoginType().getType(),
                user.getSocialId(),
                user.getTotalAnswerCount(),
                user.getAdoptedAnswerCount()
        );
    }
}
