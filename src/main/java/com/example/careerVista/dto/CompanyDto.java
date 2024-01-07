package com.example.careerVista.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto
{
    private Integer id;
    private String name;

    private String ceoName;
    private String foundingDate;


}
