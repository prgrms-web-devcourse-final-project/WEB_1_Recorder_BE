package com.revup.user.service;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.dto.Certification;
import com.revup.user.dto.Email;
import com.revup.user.entity.Affiliation;
import com.revup.user.entity.Profile;
import com.revup.user.entity.User;

public interface UserService {

    User findById(Long id);

    User findByTokenInfo(TokenInfo tokenInfo);

    User register(User user);

    Profile updateProfile(User user, Profile profile);

    Affiliation updateEmail(User user, Certification certification, Email email);
}
