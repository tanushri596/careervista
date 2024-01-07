package com.example.careerVista.dao;

import com.example.careerVista.dto.UserChatDto;
import com.example.careerVista.entity.Chat;
import com.example.careerVista.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatDao extends JpaRepository<Chat,Integer>
{
    @Query("SELECT new com.example.careerVista.dto.UserChatDto(c.receiver.id,c.receiver.firstName,c.receiver.lastName) FROM Chat c WHERE c.sender.id = :userId")
    List<UserChatDto> findBySenderId(Integer userId);

    @Query("SELECT new com.example.careerVista.dto.UserChatDto(c.sender.id,c.sender.firstName,c.sender.lastName) FROM Chat c WHERE c.receiver.id = :userId")
    List<UserChatDto> findByReceiverId(Integer userId);


    List<Chat> findAllBySenderId(Integer userId);
    List<Chat> findAllByReceiverId(Integer userId);
}
