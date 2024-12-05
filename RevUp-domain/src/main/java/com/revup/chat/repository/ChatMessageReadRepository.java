package com.revup.chat.repository;

import com.revup.chat.entity.ChatMessageRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageReadRepository extends JpaRepository<ChatMessageRead, Long> {

}
