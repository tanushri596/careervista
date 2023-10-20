package com.example.careerVista.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private String description;



    private Date startTime;
    private Date endTime;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
