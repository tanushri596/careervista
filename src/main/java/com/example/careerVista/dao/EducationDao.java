package com.example.careerVista.dao;

import com.example.careerVista.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationDao extends JpaRepository<Education,Integer>
{
    List<Education>findAllByUserId(Integer userId);
}
