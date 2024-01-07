package com.example.careerVista.dao;

import com.example.careerVista.dto.ApplicationDto;
import com.example.careerVista.entity.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationsDao extends JpaRepository<Applications,Integer> {

//    public List<Applications> findAllByUserId(Integer userId);


    List<Applications> findAllByUserId(Integer userId);
    @Query("SELECT new com.example.careerVista.dto.ApplicationDto(" +
            "a.id, a.role, a.status, a.active, a.withdrawn, a.applyDate, " +
            "new com.example.careerVista.dto.JobDto(" +
            "a.job.id, a.job.role, a.job.description, a.job.vacancy, a.job.salary, " +
            "a.job.location, a.job.postDate, a.job.status, a.job.user.id, a.job.company.id, a.job.company.name" +
            "), " +
            "a.company.id, " +
            "new com.example.careerVista.dto.UserApplicationDto(a.user.id, a.user.firstName, a.user.lastName)" +
            ") " +
            "FROM Applications a " +
            "WHERE a.job.id = :jobId")
    List<ApplicationDto> findByJobId(Integer jobId);

    public List<Applications> findAllByJobId(Integer jobId);

    @Query("SELECT new com.example.careerVista.dto.ApplicationDto(" +
            "a.id, a.role, a.status, a.active, a.withdrawn, a.applyDate, " +
            "new com.example.careerVista.dto.JobDto(" +
            "a.job.id, a.job.role, a.job.description, a.job.vacancy, a.job.salary, " +
            "a.job.location, a.job.postDate, a.job.status, a.job.user.id, a.job.company.id, a.job.company.name" +
            "), " +
            "a.company.id, " +
            "new com.example.careerVista.dto.UserApplicationDto(a.user.id, a.user.firstName, a.user.lastName)" +
            ") " +
            "FROM Applications a " +
            "WHERE a.company.id = :compId")
    List<ApplicationDto> findAllByCompanyId(Integer compId);


}
