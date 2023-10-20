package com.example.careerVista.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Company
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private byte[] image;
    private String email;
    private Integer no_of_employees;
    private String ceoName;
    private String description;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Job> jobs;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<User> users;
}
