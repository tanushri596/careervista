package com.example.careerVista.controller;

import com.example.careerVista.entity.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public interface CandidateController {

    @PostMapping({"/createUser"})
    public void createUser(@RequestBody User user);

//    Education APIs

    @PostMapping({"/addEducation"})
    public void  addEducation(@RequestBody Education education);
    @GetMapping({"/getEducation/{id}"})
    public List<Education>getEducationList(@PathVariable Integer id);
    @DeleteMapping({"/deleteEducation/{id}"})
    public void deleteEducation(@PathVariable Integer id);

    // Experience APIs

    @PostMapping({"/addExperience"})
    public void addExperience(@RequestBody Experience experience);
    @GetMapping({"/getExperience/{id}"})
    public List<Experience> getExperienceList(@PathVariable Integer id);
    @DeleteMapping({"/deleteExperience/{id}"})
    public void deleteExperience(@PathVariable Integer id);

    // Project APIs

    @PostMapping({"/addProject"})
    public void addProject(@RequestBody Project project);
    @GetMapping({"/getProject/{userId}"})
    public List<Project> getProjectList(@PathVariable Integer userId);
    @DeleteMapping({"/deleteProject/{id}"})
    public void deleteProject(@PathVariable Integer id);

    // Applications APIs

    @PostMapping({"/addApplications"})
    public void addApplications(@RequestBody Applications application);
    @GetMapping({"/getApplicationsByUserId/{userId}"})
    public List<Applications> getApplicationsByUserId(@PathVariable Integer userId);
    @GetMapping({"/getApplicationsByJobId/{jobId}"})
    public List<Applications> getApplicationsByJobId(@PathVariable Integer jobId);


// Job APIs

    @PostMapping({"/addJob"})
    public void addJob(@RequestBody Job job);
    @GetMapping({"/getJobsByUserId/{userId}"})
    public List<Job> getAllJobsByUserId(@PathVariable Integer userId);
    @DeleteMapping({"/deleteJob/{id}"})
    public void deleteJobById(@PathVariable Integer id);




}
