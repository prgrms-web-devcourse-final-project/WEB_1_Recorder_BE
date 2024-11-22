package com.revup.oauth.response;

public record OIDCPublicKeysResponse(
        String kid,
        String kty,
        String alg,
        String use,
        String n,
        String e
) {
}
