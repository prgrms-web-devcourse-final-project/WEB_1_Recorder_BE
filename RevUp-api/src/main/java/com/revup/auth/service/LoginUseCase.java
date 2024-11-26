package com.revup.auth.service;


import com.revup.annotation.UseCase;
import com.revup.auth.model.dto.response.FirstLoginResponse;
import com.revup.auth.model.mapper.AuthMapper;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserUtil userUtil;
    private final AuthMapper authMapper;

    public FirstLoginResponse execute() {
        User user = userUtil.getCurrentUser();
        log.info("user = {}", user);
        return authMapper.toFirstLoginResponse(user.getProfile());
    }
}
