package com.example.careerVista.entity;

import jakarta.persistence.*;

@Entity
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
    private User user;
}

