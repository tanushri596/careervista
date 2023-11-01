package com.example.careerVista.dao;

import com.example.careerVista.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ExperienceDao extends JpaRepository<Experience,Integer> {

    public List<Experience> findAllByUserId(Integer userId);
}
