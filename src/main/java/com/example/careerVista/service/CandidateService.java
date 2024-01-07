package com.example.careerVista.service;

import com.example.careerVista.dto.*;
import com.example.careerVista.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface CandidateService
{

   //user


   public ResponseEntity<String> signUp(UserSignDto requestMap);
   public ResponseEntity<String> logIn(UserLogDto requestMap);
  public UserDto getCandidate(String username);
   List<User> getAllUsers();
   public void deleteCandidate(Integer userId);

   //Education
   public void addEducation(EducationDto education);
   public List<EducationDto> getEducationByUserId(Integer userId);
   public void deleteEducationById(Integer eduId);

   //Experience

   public void addExperience(ExperienceDto experience);
   public List<ExperienceDto> getExperiencesByUserId(Integer userId);
   public void deleteExperienceById(Integer expId);

   //Project

   public void addProject(ProjectDto project);
   public List<ProjectDto> getProjectsByUserId(Integer userId);

   public void deleteProject(Integer proId);

   //Applications

   public void addApplication(ApplicationDto application);
   public List<ApplicationDto> getApplicationsByUserId(Integer userId);
   public List<ApplicationDto> getApplicationsByJobId(Integer jobId);
   public void updateApplicationActivity(Boolean active,Integer applicationId);
   public void updateApplicationStatus(String status, Integer applicationId);



   //Jobs

   public void addJob(JobDto job);

 public List<JobDto> getAllJobsByUserId(Integer userId);

   public void removeJobById(Boolean status,Integer jobId);


   Page<JobDto> getAllJobs(Pageable pageable,List<String>roles,List<String>locations);

   void deleteApplicationById(Integer appId);

   boolean getCandidateViaUsername(String username);

    void updateApplicationWithdrawStatus(Boolean withdrawn, Integer appId);

    void updateCandidate(UserSignDto requestBody);

    List<JobDto> getAllTheJobs();

    List<ApplicationDto> getApplicationsByCompId(Integer compId);
}
