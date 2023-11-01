package com.example.careerVista.dao;

import com.example.careerVista.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobDao extends JpaRepository<Job,Integer>
{
    public List<Job> findAllByUserId(Integer userId);
    public List<Job> findAllByCompanyId(Integer compId);

}
