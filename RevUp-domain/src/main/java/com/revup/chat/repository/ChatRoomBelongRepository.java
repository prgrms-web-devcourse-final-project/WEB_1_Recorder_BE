package com.revup.chat.repository;

import com.revup.chat.entity.ChatRoom;
import com.revup.chat.entity.ChatRoomBelong;
import com.revup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomBelongRepository extends JpaRepository<ChatRoomBelong, Long> {

    List<ChatRoomBelong> findByUser(User user);

    Optional<ChatRoomBelong> findByUserAndChatRoom(User user, ChatRoom chatRoom);

    Optional<ChatRoomBelong> findByChatRoom(ChatRoom chatRoom);

    default Optional<ChatRoom> findChatRoomByTwoUsers(User user1, User user2) {
        List<ChatRoomBelong> user1Belongs = findByUser(user1);
        List<ChatRoomBelong> user2Belongs = findByUser(user2);

        return user1Belongs.stream()
                .map(ChatRoomBelong::getChatRoom)
                .filter(chatRoom -> user2Belongs.stream()
                        .map(ChatRoomBelong::getChatRoom)
                        .anyMatch(user2ChatRoom -> user2ChatRoom.equals(chatRoom)))
                .findFirst();
    }

}
