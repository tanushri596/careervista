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
public class EducationDto
{
    private Integer id;
    private String courseName;
    private String institution;

    private Double marks;

    private String startDate;
    private String endDate;

    private Integer userId;
}
