package com.revup.user.service;

import com.revup.user.dto.Email;
import com.revup.user.entity.Profile;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdater {

    private final UserUtil userUtil;

    public User updateProfile(Profile profile) {
        User currentUser = userUtil.getCurrentUser();
        currentUser.updateProfile(profile);
        return currentUser;
    }

    public User updateEmail(Email email) {
        User currentUser = userUtil.getCurrentUser();
        currentUser.updateBusinessEmail(email);
        return currentUser;
    }
}
