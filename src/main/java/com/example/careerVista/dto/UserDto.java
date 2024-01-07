package com.example.careerVista.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
    private String status;

    private String birthDate;
    private String designation;
    private Integer companyId;




}
