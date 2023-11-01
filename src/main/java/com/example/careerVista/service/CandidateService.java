package com.example.careerVista.service;

import com.example.careerVista.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface CandidateService
{

   //user

   public void addCandidate(User user);
   public ResponseEntity<String> signUp(Map<String,String> requestMap);
   public void deleteCandidate(Integer userId);

   //Education
   public void addEducation(Education education);
   public List<Education> getEducationByUserId(Integer userId);
   public void deleteEducationById(Integer id);

   //Experience

   public void addExperience(Experience experience);
   public List<Experience> getExperiencesByUserId(Integer id);
   public void deleteExperienceById(Integer id);

   //Project

   public void addProject(Project project);
   public List<Project> getProjectsByUserId(Integer userId);

   public void deleteProject(Integer id);

   //Applications

   public void addApplication(Applications application);
   public List<Applications> getApplicationsByUserId(Integer userId);
   public List<Applications> getApplicationsByJobId(Integer jobId);
   public void updateApplicationActivity(Boolean active,Integer applicationId);
   public void updateApplicationStatus(String status, Integer applicationId);



   //Jobs

   public void addJob(Job job);
   
 public List<Job> getAllJobsByUserId(Integer userId);

   public void deleteJobById(Integer id);


}
