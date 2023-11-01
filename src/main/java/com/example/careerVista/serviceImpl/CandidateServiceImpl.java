package com.example.careerVista.serviceImpl;

import com.example.careerVista.dao.*;
import com.example.careerVista.entity.*;
import com.example.careerVista.service.CandidateService;
import com.example.careerVista.utils.CareerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
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

    // Users
    @Override
    public void addCandidate(User user) {
        System.out.println("service: " + user.toString());
       userDao.save(user);
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap)
    {
        if(validateSignUpMap(requestMap))
        {
            log.info("inside sign up",requestMap);
            User user = userDao.findByEmailId(requestMap.get("email"));

            if(Objects.isNull(user))
            {
              User newUser = getUserFromMap(requestMap);
              userDao.save(newUser);
              return CareerUtils.getResponseEntity("Successfully registered",HttpStatus.OK);
            }
            else
            {
                return CareerUtils.getResponseEntity("User Already exists",HttpStatus.BAD_REQUEST);
            }

        }
        else
            return CareerUtils.getResponseEntity("Invalid User Information entered", HttpStatus.BAD_REQUEST);
    }

    public User getUserFromMap(Map<String,String>requestMap)
    {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setPhoneNumber(requestMap.get("phoneNumber"));
        user.setCompany(null);
        user.setImage(null);
        user.setSkills(null);
        user.setRoles(null);

        return user;
    }

    private Boolean validateSignUpMap(Map<String,String>requestMap)
    {
        if(requestMap.containsKey("name") && requestMap.containsKey("email") &&
            requestMap.containsKey("phoneNumber") && requestMap.containsKey("password"))
            return true;

        return false;
    }

    @Override
    public void deleteCandidate(Integer userId)
    {
        Optional<User> candidate = userDao.findById(userId);

        if(candidate.isPresent())
        {
            List<Job> userJobs = getAllJobsByUserId(userId);
            User user = candidate.get();
            Company company = user.getCompany();

            for (Job job : userJobs) {


                job.setUser(null);
                if(company == null)
                jobDao.delete(job);
            }

            List<Applications> userApplications = getApplicationsByUserId(userId);
            for (Applications application : userApplications) {
                application.setUser(null);
                applicationsDao.delete(application);
            }

            List<Education> userEducation = getEducationByUserId(userId);
            for (Education education : userEducation) {
                education.setUser(null);
                educationDao.delete(education);
            }

            List<Experience> userExperience = getExperiencesByUserId(userId);
            for (Experience experience : userExperience) {
                experience.setUser(null);
                experienceDao.delete(experience);
            }

            List<Project> userProject = getProjectsByUserId(userId);
            for (Project project : userProject) {
                project.setUser(null);
                projectDao.delete(project);
            }
            userDao.deleteById(userId);
        }
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
    public List<Project> getProjectsByUserId(Integer userId) {
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
    public void updateApplicationActivity(Boolean active,Integer applicationId)
    {
       Optional<Applications> userApplication = applicationsDao.findById(applicationId);

       if(userApplication.isPresent())
       {
           Applications application = userApplication.get();
           application.setActive(active);
           applicationsDao.save(application);
       }
    }

    @Override
    public void updateApplicationStatus(String status, Integer applicationId)
    {
        Optional<Applications> userApplication = applicationsDao.findById(applicationId);

        if(userApplication.isPresent())
        {
            Applications application = userApplication.get();
            application.setStatus(status);
            applicationsDao.save(application);
        }
    }

    // jobs
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
          List<Applications> userApplications = applicationsDao.findAllByJobId(id);

          for(Applications application : userApplications)
          {
               application.setJob(null);
               applicationsDao.delete(application);
          }

          jobDao.deleteById(id);
      }
    }




}
