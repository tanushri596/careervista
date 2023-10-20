package com.example.careerVista.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Education
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String institution;

    private Double marks;

    private Date StartTime;
    private Date endTime;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
