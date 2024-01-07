package com.example.careerVista.dto;


import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto
{
    private Integer id;
    private String role;

    private String status;
    private Boolean active;
    private Boolean withdrawn;
    private String applyDate;


    private JobDto jobDto;


    private Integer companyId;
    private String companyName;


    private UserApplicationDto userDto;
}
