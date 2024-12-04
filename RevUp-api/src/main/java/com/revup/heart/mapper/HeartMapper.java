package com.revup.heart.mapper;

import com.revup.heart.dto.request.HeartCreateRequest;
import com.revup.heart.entity.Heart;
import com.revup.heart.enums.HeartType;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HeartMapper {
    public Heart toEntity(HeartCreateRequest request, User currentUser) {
        return Heart.builder()
                .type(HeartType.of(request.type()))
                .user(currentUser)
                .build();
    }

}
