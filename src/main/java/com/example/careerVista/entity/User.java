package com.example.careerVista.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="_user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String availability;

    private byte[] image;
    private String email;
    private String password;

    private Integer phone_number;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Applications> applications;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(
            name="USER_SKILL",
            joinColumns = {
                    @JoinColumn(name="USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="SKILL_ID")
            }
    )
    private Set<Skills> skills;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(
            name="USER_ROLE",
            joinColumns = {
                    @JoinColumn(name="USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="ROLE_ID")
            }
    )
    private Set<Role> roles;

}
