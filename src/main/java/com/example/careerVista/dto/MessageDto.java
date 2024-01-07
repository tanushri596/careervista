package com.example.careerVista.dto;

import com.example.careerVista.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto
{
    private Integer id;
    private String channel;
    private String time;
    private String message;
    private UserChatDto sender;
    private UserChatDto receiver;
}
