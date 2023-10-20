package com.example.careerVista.entity;

import jakarta.persistence.*;

@Entity
@Table(name="_role")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
