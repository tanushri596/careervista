package com.example.careerVista.entity;

import com.example.careerVista.dto.CompanyDto;
import com.example.careerVista.dto.JobDto;
import com.example.careerVista.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Applications
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role;

    private String status;
    private Boolean active;
    private Boolean withdrawn;
    private String applyDate;
    private String companyName;

    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user; // this indicates which user has applied for the job
}

