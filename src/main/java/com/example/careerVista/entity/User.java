package com.example.careerVista.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="_user")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
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

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", availability='" + availability + '\'' +
//                ", image=" + Arrays.toString(image) +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", phone_number=" + phone_number +
//                ", company=" + company +
//                ", skills=" + skills +
//                ", roles=" + roles +
//                '}';
//    }

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;



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
