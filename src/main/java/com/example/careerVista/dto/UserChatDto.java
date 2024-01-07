package com.example.careerVista.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChatDto
{
    private Integer id;
    private String firstName;
    private String lastName;
}
