package com.revup.answer.dto.request;

import com.revup.global.validation.BlockedContent;
import com.revup.image.dto.request.ImageRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AnswerCreateRequest(
        Long questionId,

        @NotBlank(message = "제목은 비어 있을 수 없습니다.")
        String title,

        @NotBlank
        @BlockedContent
        @Size(max = 65535, message = "내용이 최대 사이즈를 초과하였습니다.")
        String content,

        AnswerCodeCreateRequest code,

        List<ImageRequest> images
) {
}
