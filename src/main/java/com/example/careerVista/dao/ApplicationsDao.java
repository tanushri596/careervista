package com.example.careerVista.dao;

import com.example.careerVista.entity.Applications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationsDao extends JpaRepository<Applications,Integer> {

    public List<Applications> findAllByUserId(Integer userId);
    public List<Applications> findAllByJobId(Integer jobId);
}
