package com.revup.user.service;

import com.revup.user.dto.Email;
import com.revup.user.entity.Profile;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdater {

    public User updateProfile(User user, Profile profile) {
        user.updateProfile(profile);
        return user;
    }

    public User updateEmail(User user, Email email) {
        user.updateBusinessEmail(email);
        return user;
    }
}
