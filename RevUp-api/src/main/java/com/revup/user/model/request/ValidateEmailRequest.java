package com.revup.user.model.request;

import jakarta.validation.constraints.Email;

public record ValidateEmailRequest(
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String email
) {
}
