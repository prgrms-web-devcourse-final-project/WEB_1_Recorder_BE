package com.revup.common.skillstack.controller;

import com.revup.common.skillstack.model.mapper.SkillStackMapper;
import com.revup.common.skillstack.model.response.SkillStackResponse;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tech")
public class SkillStackController {

    private final SkillStackMapper skillStackMapper;

    @GetMapping()
    public ResponseEntity<ApiResponse<SkillStackResponse>> getSKillStacks() {
        return success(skillStackMapper.toSkillStackResponse());
    }
}
