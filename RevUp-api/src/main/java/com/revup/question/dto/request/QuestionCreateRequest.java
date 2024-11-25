package com.revup.question.dto.request;

import com.revup.global.validation.BlockedContent;
import com.revup.image.dto.request.ImageRequest;
import com.revup.tag.dto.request.TagRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestionCreateRequest(
        @NotBlank(message = "제목은 비어 있을 수 없습니다.")
        String title,

        @BlockedContent
        @NotBlank(message = "내용은 비어 있을 수 없습니다.")
        @Size(max = 65535, message = "내용은 최대 사이즈를 초과하였습니다.")
        String content,

        String type,

        boolean isTemporary,

        boolean isAnonymous,

        List<TagRequest> tags,

        List<ImageRequest> images
) {
}
