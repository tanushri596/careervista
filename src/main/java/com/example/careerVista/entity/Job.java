package com.example.careerVista.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Job
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String role;
   private String description;
   private Integer vacancy;
   private Integer salary;
   private String location;

   @ManyToOne
   @JoinColumn(name="company_id")
   private Company company;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user; // this indicates which user is posting the job

}
