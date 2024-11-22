package com.revup.question.dto.request;
import com.revup.image.dto.request.QuestionImageRequest;
import com.revup.tag.dto.request.TagRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestionCreateRequest(
        @NotBlank(message = "제목은 비어 있을 수 없습니다.")
        String title,

        @NotBlank(message = "내용은 비어 있을 수 없습니다.")
        @Size(max = 65535, message = "내용은 최대 65,535자까지 입력할 수 있습니다.")
        String content,

        String type,

        boolean isTemporary,

        boolean isAnonymous,

        List<TagRequest> tags,

        List<QuestionImageRequest> images
) {
}
