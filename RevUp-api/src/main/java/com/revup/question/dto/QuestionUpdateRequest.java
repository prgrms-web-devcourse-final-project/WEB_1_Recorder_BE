package com.revup.question.dto;

import com.revup.global.validation.BlockedContent;
import com.revup.image.dto.request.ImageRequest;
import com.revup.question.dto.request.QuestionCodeCreateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestionUpdateRequest(

        Long id,

        @NotBlank(message = "제목은 비어 있을 수 없습니다.")
        String title,

        @BlockedContent
        @NotBlank(message = "내용은 비어 있을 수 없습니다.")
        @Size(max = 65535, message = "내용이 최대 사이즈를 초과하였습니다.")
        String content,

        boolean githubLinkReveal,

        String githubLink,

        String type,

        boolean isTemporary,

        boolean isAnonymous,

        List<String> stacks,

        List<QuestionCodeCreateRequest> codes,

        List<ImageRequest> images
) {
}
