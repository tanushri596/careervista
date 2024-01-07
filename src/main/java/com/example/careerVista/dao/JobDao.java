package com.example.careerVista.dao;

import com.example.careerVista.dto.JobDto;
import com.example.careerVista.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JobDao extends JpaRepository<Job,Integer>
{
    @Query("SELECT new com.example.careerVista.dto.JobDto(j.id, j.role, j.description, j.vacancy, j.salary, j.location, j.postDate, j.status, j.user.id, j.company.id, j.company.name) FROM Job j WHERE j.user.id = :userId")
    public List<JobDto> findJobDetailsByUserId(Integer userId);

    public List<Job> findAllByCompanyId(Integer compId);
      Page<Job> findAll(Pageable pageable);

}
