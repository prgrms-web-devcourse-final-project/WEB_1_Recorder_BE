package com.revup.oauth.response;


import java.util.List;

public record OIDCPublicKeysResponses(
        List<OIDCPublicKeysResponse> keys
) {
}
