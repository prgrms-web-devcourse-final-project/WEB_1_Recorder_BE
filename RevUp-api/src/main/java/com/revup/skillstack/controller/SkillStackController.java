package com.revup.skillstack.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.skillstack.model.response.SkillStackResponse;
import com.revup.skillstack.usecase.SkillStackUseCase;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tech")
public class SkillStackController {

    private final SkillStackUseCase skillStackUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<SkillStackResponse>> getAllStacks() {
        return success(skillStackUseCase.getAllStacks());
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<SkillStackResponse>> getPopularStacks(@RequestParam @Max(10) int size) {
        return success(skillStackUseCase.getPopulars(size));
    }

}
