package com.example.careerVista.dao;

import com.example.careerVista.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDao extends JpaRepository<Project,Integer> {

    public List<Project> findAllByUserId(Integer userId);
}
