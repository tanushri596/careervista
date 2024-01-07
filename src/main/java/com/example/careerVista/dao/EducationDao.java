package com.example.careerVista.dao;

import com.example.careerVista.dto.EducationDto;
import com.example.careerVista.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationDao extends JpaRepository<Education,Integer>
{

    @Query("SELECT new com.example.careerVista.dto.EducationDto(e.id, e.courseName, e.institution, e.marks, e.startDate, e.endDate, e.user.id) FROM Education e WHERE e.user.id = :userId")
    List<EducationDto>findByUserId(Integer userId);

    List<Education>findAllByUserId(Integer userId);
}
