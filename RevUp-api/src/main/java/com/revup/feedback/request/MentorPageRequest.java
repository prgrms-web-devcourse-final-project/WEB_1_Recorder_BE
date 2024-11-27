package com.revup.feedback.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class MentorPageRequest {

    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
    private int page;

}
