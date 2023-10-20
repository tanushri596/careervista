package com.example.careerVista.entity;

import jakarta.persistence.*;

@Entity
public class Skills
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;


}
