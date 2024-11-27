package com.revup.user.service;

import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserRegistrar {

    private final UserAdaptor userAdaptor;

    public User register(User user) {
        return userAdaptor.save(user);
    }
}
