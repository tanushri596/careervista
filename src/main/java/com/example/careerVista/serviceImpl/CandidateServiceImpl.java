package com.example.careerVista.serviceImpl;

import com.example.careerVista.dao.*;
import com.example.careerVista.entity.*;
import com.example.careerVista.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private EducationDao educationDao;

    @Autowired
    private ExperienceDao experienceDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ApplicationsDao applicationsDao;

    @Autowired
    private JobDao jobDao;
    @Override
    public void addCandidate(User user) {
        System.out.println("service: " + user.toString());
       userDao.save(user);
    }

    //Education

    @Override
    public void addEducation(Education education) {
        System.out.println("In service education" + education);
       educationDao.save(education);
    }



    @Override
    public List<Education> getEducationByUserId(Integer id) {
    List<Education> education = educationDao.findAllByUserId(id);

    return education;
}

    @Override
    public void deleteEducationById(Integer id)
    {
        Optional<Education> education = educationDao.findById(id);

        if (education.isPresent()) {
           educationDao.deleteById(id);
        }


    }
     //Experience

    @Override
    public void addExperience(Experience experience)
    {
        experienceDao.save(experience);
    }

    @Override
    public List<Experience> getExperiencesByUserId(Integer userId) {
        return experienceDao.findAllByUserId(userId);
    }

    @Override
    public void deleteExperienceById(Integer id) {
        Optional<Experience> experience = experienceDao.findById(id);

        if (experience.isPresent()) {
            experienceDao.deleteById(id);
        }
    }

    //Projects
    @Override
    public void addProject(Project project)
    {
       projectDao.save(project);
    }

    @Override
    public List<Project> getProjects(Integer userId) {
        return projectDao.findAllByUserId(userId);
    }

    public void deleteProject(Integer id)
    {
        Optional<Project> project = projectDao.findById(id);

        if (project.isPresent()) {
            projectDao.deleteById(id);
        }
    }

    // Applications
    @Override
    public void addApplication(Applications application)
    {
        applicationsDao.save(application);
    }

    @Override
    public List<Applications> getApplicationsByUserId(Integer userId) {
        return applicationsDao.findAllByUserId(userId);
    }

    @Override
    public List<Applications> getApplicationsByJobId(Integer jobId) {
        return applicationsDao.findAllByJobId(jobId);
    }

    @Override
    public void addJob(Job job) {
     jobDao.save(job);
    }

    @Override
    public List<Job> getAllJobsByUserId(Integer userId) {

        return jobDao.findAllByUserId(userId);
    }

    @Override
    public void deleteJobById(Integer id) {
      Optional<Job> job = jobDao.findById(id);

      if(job.isPresent())
      {
          jobDao.deleteById(id);
      }
    }


}
