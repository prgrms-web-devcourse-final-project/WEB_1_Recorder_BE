package com.revup.heart.usecase;

import com.revup.heart.service.HeartService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteHeartUseCase {
    private final HeartService heartService;

    public void execute(Long id, User currentUser){
        heartService.deleteHeart(id, currentUser);
    }
}
