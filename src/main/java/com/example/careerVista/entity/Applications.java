package com.example.careerVista.entity;

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
    private String status;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user; // this indicates which user has applied for the job
}

