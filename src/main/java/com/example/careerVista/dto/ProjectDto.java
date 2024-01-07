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
public class ProjectDto
{
    private Integer id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Integer userId;
}
