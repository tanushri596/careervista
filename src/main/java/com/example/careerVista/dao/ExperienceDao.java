package com.example.careerVista.dao;

import com.example.careerVista.dto.ExperienceDto;
import com.example.careerVista.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ExperienceDao extends JpaRepository<Experience,Integer> {

    @Query("SELECT new com.example.careerVista.dto.ExperienceDto(e.id, e.company, e.role,e.description, e.startDate, e.endDate, e.user.id) FROM Experience e WHERE e.user.id = :userId")
    public List<ExperienceDto> findByUserId(Integer userId);

    public List<Experience> findAllByUserId(Integer userId);
}
