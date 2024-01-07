package com.example.careerVista.dao;

import com.example.careerVista.dto.ProjectDto;
import com.example.careerVista.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectDao extends JpaRepository<Project,Integer> {

    @Query("SELECT new com.example.careerVista.dto.ProjectDto(p.id, p.name,p.description, p.startDate, p.endDate, p.user.id) FROM Project p WHERE p.user.id = :userId")

    public List<ProjectDto> findByUserId(Integer userId);

    public List<Project> findAllByUserId(Integer userId);
}
