package com.revup.auth.model.dto.response;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public record FirstLoginResponse(
        boolean isFirst
) {
}
