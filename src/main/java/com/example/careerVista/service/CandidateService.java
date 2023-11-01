package com.example.careerVista.service;

import com.example.careerVista.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CandidateService
{
   public void addCandidate(User user);

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
   public List<Project> getProjects(Integer userId);

   public void deleteProject(Integer id);

   //Applications

   public void addApplication(Applications application);
   public List<Applications> getApplicationsByUserId(Integer userId);
   public List<Applications> getApplicationsByJobId(Integer jobId);

   //Jobs

   public void addJob(Job job);
   
 public List<Job> getAllJobsByUserId(Integer userId);

   public void deleteJobById(Integer id);
}
