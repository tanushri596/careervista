package com.example.careerVista.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Job
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String role;
   private String description;
   private Integer vacancy;
   private Integer salary;

   @ManyToOne
   @JoinColumn(name="company_id")
   private Company company;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Applications> applications;
}
