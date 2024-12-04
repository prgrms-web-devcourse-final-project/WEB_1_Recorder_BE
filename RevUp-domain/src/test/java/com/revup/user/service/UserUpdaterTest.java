package com.revup.user.service;

import com.revup.user.entity.Profile;
import com.revup.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserUpdaterTest {

    @InjectMocks
    private UserDomainImpl userService;

    @Test
    @DisplayName("유저 프로필 변경")
    void changeUserProfileTest() {
        User newUser = User.builder().build();

        Profile newProfile = Profile.builder().build();
        Profile updatedProfile = userService.updateProfile(newUser, newProfile);

        assertThat(updatedProfile).isNotNull();
    }

}