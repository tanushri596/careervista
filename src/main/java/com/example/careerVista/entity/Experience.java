package com.example.careerVista.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Experience
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String company;
    private String role;
    private String description;
    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
