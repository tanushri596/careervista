package com.example.careerVista.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    private Integer id;
    private String role;
    private String description;
    private Integer vacancy;
    private Integer salary;
    private String location;
    private String postDate;
    private Boolean status;
    private Integer userId;
    private Integer companyId;
    private String companyName;



}
