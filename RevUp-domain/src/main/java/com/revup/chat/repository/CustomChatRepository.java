package com.revup.chat.repository;

import com.revup.chat.entity.Chat;
import com.revup.user.entity.User;

import java.util.List;

public interface CustomChatRepository {

    List<Chat> findRecentChats(User user);

}
