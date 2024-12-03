package com.revup.user.model.response;

import com.revup.user.entity.Affiliation;

public record UpdateAffiliationResponse(
        String email,
        String affiliationName
) {

    public static UpdateAffiliationResponse of(Affiliation affiliation) {
        return new UpdateAffiliationResponse(
                affiliation.getBusinessEmail(),
                affiliation.getAffiliationName()
        );
    }
}
