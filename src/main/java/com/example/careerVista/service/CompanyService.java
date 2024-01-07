package com.example.careerVista.service;

import com.example.careerVista.dto.CompanyDto;
import com.example.careerVista.dto.JobDto;
import com.example.careerVista.dto.UserDto;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CompanyService
{
  ResponseEntity<String> signUp(Map<String, String> requestMap);
  ResponseEntity<String> logIn(Map<String, String> requestMap);

  List<Company> getAllCompanies();
  public List<JobDto> getJobs(Integer compId);

  public List<Applications> getApplications(Integer jobId);
  public List<UserDto> getEmployeesByCompanyId(Integer compId);

  public void deleteCompany(Integer compId);

  void updateApplicationStatusByCompany(String status, Integer applicationId);


    boolean getCompanyViaUsername(String username);

    CompanyDto getCompanyByUsername(String username);

  void updateCandidateStatus(String status, Integer userId);
}
