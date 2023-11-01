package com.example.careerVista.controllerImpl;

import com.example.careerVista.controller.CandidateController;
import com.example.careerVista.entity.*;
import com.example.careerVista.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CandidateControllerImpl implements CandidateController {

    @Autowired
     private CandidateService candidateService;


    @Override
    public void createUser(User user) {

      candidateService.addCandidate(user);
    }


    // Education
    @Override
    public void addEducation(Education education) {
      candidateService.addEducation(education);
    }

    @Override
    public List<Education> getEducationList(Integer id) {
        return candidateService.getEducationByUserId(id);
    }

    @Override
    public void deleteEducation(Integer id) {
        candidateService.deleteEducationById(id);
    }


    //Experience
    @Override
    public void addExperience(Experience experience)
    {
       candidateService.addExperience(experience);
    }

    @Override
    public List<Experience> getExperienceList(Integer id) {

        return candidateService.getExperiencesByUserId(id);
    }

    @Override
    public void deleteExperience(Integer id) {
      candidateService.deleteExperienceById(id);
    }


    //Project
    @Override
    public void addProject(Project project)
    {
       candidateService.addProject(project);
    }

    @Override
    public List<Project> getProjectList(Integer userId) {
       return candidateService.getProjects(userId);
    }

    @Override
    public void deleteProject(Integer id) {
        candidateService.deleteProject(id);
    }


    //Applications
    @Override
    public void addApplications(Applications application)
    {
       candidateService.addApplication(application);
    }

    @Override
    public List<Applications> getApplicationsByUserId(Integer userId) {

       return candidateService.getApplicationsByUserId(userId);
    }

    @Override
    public List<Applications> getApplicationsByJobId(Integer jobId) {
        return candidateService.getApplicationsByJobId(jobId);
    }


    //Jobs

    @Override
    public void addJob(Job job)
    {
      candidateService.addJob(job);
    }

    @Override
    public List<Job> getAllJobsByUserId(Integer userId) {

        return candidateService.getAllJobsByUserId(userId);
    }

    @Override
    public void deleteJobById(Integer id) {
      candidateService.deleteJobById(id);
    }
}