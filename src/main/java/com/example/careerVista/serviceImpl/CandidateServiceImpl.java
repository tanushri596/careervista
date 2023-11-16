package com.example.careerVista.serviceImpl;

import com.example.careerVista.Jwt.CandidateDetailsService;
import com.example.careerVista.Jwt.JwtFilter;
import com.example.careerVista.Jwt.JwtUtil;
import com.example.careerVista.dao.*;
import com.example.careerVista.dto.UserLogDto;
import com.example.careerVista.dto.UserSignDto;
import com.example.careerVista.entity.*;
import com.example.careerVista.service.CandidateService;
import com.example.careerVista.utils.CareerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;

@Slf4j
@Service
public class CandidateServiceImpl implements CandidateService {
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

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CandidateDetailsService candidateDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;

    // Users


    @Override
    public ResponseEntity<String> signUp(UserSignDto requestMap) {
        if (validateSignUpMap(requestMap)) {
            log.info("inside sign up", requestMap);
            User user = userDao.findAllByUsername(requestMap.getUsername());

            if (Objects.isNull(user)) {
                User newUser = getUserFromMap(requestMap);
                userDao.save(newUser);
                return CareerUtils.getResponseEntity("Successfully registered", HttpStatus.OK);
            } else {
                return CareerUtils.getResponseEntity("Email Already exists", HttpStatus.BAD_REQUEST);
            }

        } else
            return CareerUtils.getResponseEntity("Invalid User Information entered", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> logIn(UserLogDto requestMap) {

        log.info("Inside login");


        User user = userDao.findAllByUsername(requestMap.getUsername());


        if (user != null && passwordEncoder.matches(CharBuffer.wrap( requestMap.getPassword()),user.getPassword())) {


            return new ResponseEntity<String>("{\"token\":\"" +
                    jwtUtil.generateToken(user.getUsername(),
                            user.getRole()) + "\",\"role\":\"" + user.getRole() + "\"}", HttpStatus.OK);


//                }
//                else{
//                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval"+"\"}",HttpStatus.BAD_REQUEST );
//                }
        }


        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials" + "\"}", HttpStatus.BAD_REQUEST);

    }

    @Override
    public User getCandidate(String username) {
        return userDao.findAllByUsername(username);
    }

    public User getUserFromMap(UserSignDto requestMap) {
        User user = new User();
        user.setFirstName(requestMap.getFirstName());
        user.setLastName(requestMap.getLastName());
        user.setUsername(requestMap.getUsername());
        user.setPassword(passwordEncoder.encode(requestMap.getPassword()));
        user.setPhoneNumber(requestMap.getPhoneNumber());
        user.setCompany(requestMap.getCompany());
        user.setBirthDate(requestMap.getBirthDate());
        user.setDesignation(requestMap.getDesignation());
        user.setRole("candidate");

        return user;
    }

    private Boolean validateSignUpMap(UserSignDto requestMap) {
        if (requestMap.getUsername() != null &&
                requestMap.getPhoneNumber() !=null && requestMap.getPassword()!=null && requestMap.getRole()!=null)
            return true;

        return false;
    }

    @Override
    public void deleteCandidate(Integer userId) {
        Optional<User> candidate = userDao.findById(userId);

        if (candidate.isPresent()) {
            List<Job> userJobs = getAllJobsByUserId(userId);
            User user = candidate.get();
            Company company = user.getCompany();

            for (Job job : userJobs) {


                job.setUser(null);
                if (company == null)
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
    public void deleteEducationById(Integer id) {
        Optional<Education> education = educationDao.findById(id);

        if (education.isPresent()) {
            educationDao.deleteById(id);
        }


    }
    //Experience

    @Override
    public void addExperience(Experience experience) {
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
    public void addProject(Project project) {
        projectDao.save(project);
    }

    @Override
    public List<Project> getProjectsByUserId(Integer userId) {
        return projectDao.findAllByUserId(userId);
    }

    public void deleteProject(Integer id) {
        Optional<Project> project = projectDao.findById(id);

        if (project.isPresent()) {
            projectDao.deleteById(id);
        }
    }

    // Applications
    @Override
    public void addApplication(Applications application) {
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
    public void updateApplicationActivity(Boolean active, Integer applicationId) {
        Optional<Applications> userApplication = applicationsDao.findById(applicationId);

        if (userApplication.isPresent()) {
            Applications application = userApplication.get();
            application.setActive(active);
            applicationsDao.save(application);
        }
    }

    @Override
    public void updateApplicationStatus(String status, Integer applicationId) {
        Optional<Applications> userApplication = applicationsDao.findById(applicationId);

        if (userApplication.isPresent()) {
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

        if (job.isPresent()) {
            List<Applications> userApplications = applicationsDao.findAllByJobId(id);

            for (Applications application : userApplications) {
                application.setJob(null);
                applicationsDao.delete(application);
            }

            jobDao.deleteById(id);
        }
    }

    @Override
    public List<Job> getAllJobs() {
        return jobDao.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }


}
