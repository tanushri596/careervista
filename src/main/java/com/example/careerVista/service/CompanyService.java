package com.example.careerVista.service;

import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService
{
  public void addCompany(Company company);
  public List<Job> getJobs(Integer compId);

  public List<Applications> getApplications(Integer jobId);
  public List<User> getEmployeesByCompanyId(Integer compId);

  public void deleteCompany(Integer compId);

  void updateApplicationStatusByCompany(String status, Integer applicationId);
}
