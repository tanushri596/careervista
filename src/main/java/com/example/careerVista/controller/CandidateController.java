package com.example.careerVista.controller;

import com.example.careerVista.dto.UserLogDto;
import com.example.careerVista.dto.UserSignDto;
import com.example.careerVista.entity.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/candidate")
public interface CandidateController
{

    // User APIs
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserSignDto requestMap);


    @PostMapping("/logIn")
    public ResponseEntity<String> login (@RequestBody UserLogDto requestMap);

    @GetMapping("/getAllCandidates")
    public List<User> getAllUsers();

    @GetMapping("/getCandidateByUsername/{username}")
    public User getCandidate(@PathVariable String username);


    @DeleteMapping({"/deleteUser/{userId}"})
    public void deleteUser(@PathVariable Integer userId);

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
    @PatchMapping({"/updateApplicationActivity/{applicationId}"})
    public void updateApplicationActivity(@RequestBody Boolean active,@PathVariable Integer applicationId);
    @PatchMapping({"/updateApplicationStatus/{applicationId}"})
    public void updateApplicationStatus(@RequestBody String status,@PathVariable Integer applicationId);



// Job APIs

    @PostMapping({"/addJob"})
    public void addJob(@RequestBody Job job);
    @GetMapping({"/getAllJobs"})
    public List<Job> getAllJobs();
    @GetMapping({"/getJobsByUserId/{userId}"})
    public List<Job> getAllJobsByUserId(@PathVariable Integer userId);
    @DeleteMapping({"/deleteJob/{id}"})
    public void deleteJobById(@PathVariable Integer id);




}
