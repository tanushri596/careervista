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
public class Education
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String courseName;
    private String institution;

    private Double marks;

    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

//    @Override
//    public String toString() {
//        return "Education{" +
//                "id=" + id +
//                ", institution='" + institution + '\'' +
//                ", marks=" + marks +
//                ", StartTime=" + startTime +
//                ", endTime=" + endTime +
//                ", user=" + user +
//                '}';
//    }
}
