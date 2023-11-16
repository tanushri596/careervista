package com.example.careerVista.dto;

import com.example.careerVista.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignDto
{
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
    private String phoneNumber;
    private String birthDate;
    private String designation;
    private Company company;
}
