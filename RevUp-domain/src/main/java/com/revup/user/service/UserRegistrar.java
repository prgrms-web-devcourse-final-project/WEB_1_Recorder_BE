package com.revup.user.service;

import com.revup.user.entity.User;
import com.revup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserRegistrar {

    private final UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }
}
