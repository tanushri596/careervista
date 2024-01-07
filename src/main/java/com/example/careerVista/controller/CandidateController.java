package com.example.careerVista.controller;

import com.example.careerVista.dto.*;
import com.example.careerVista.entity.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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



    @GetMapping("/getCandidateByUsername/{username}")
    public UserDto getCandidate(@PathVariable String username);

    @PostMapping("/addChat")
    public void addChat(@RequestBody Chat chatDetails);

    @GetMapping("/getChats/{userId}")
    public HashSet<UserChatDto> getChats(@PathVariable Integer userId);

    @GetMapping("/getCandidateViaUsername/{username}")
    public boolean getCandidateViaUsername(@PathVariable String username);

    @PutMapping("/updateCandidate/{username}")
    public void updateCandidate(@RequestBody UserSignDto requestBody);

    @DeleteMapping({"/deleteUser/{userId}"})
    public void deleteUser(@PathVariable Integer userId);

//    Education APIs

    @PostMapping({"/addEducation"})
    public ResponseEntity<?>  addEducation(@RequestBody EducationDto education);
    @GetMapping({"/getEducation/{userId}"})
    public List<EducationDto>getEducationList(@PathVariable Integer userId);
    @DeleteMapping({"/deleteEducation/{eduId}"})
    public void deleteEducation(@PathVariable Integer eduId);

    // Experience APIs

    @PostMapping({"/addExperience"})
    public void addExperience(@RequestBody ExperienceDto experience);
    @GetMapping({"/getExperience/{userId}"})
    public List<ExperienceDto> getExperienceList(@PathVariable Integer userId);
    @DeleteMapping({"/deleteExperience/{expId}"})
    public void deleteExperience(@PathVariable Integer expId);

    // Project APIs

    @PostMapping({"/addProject"})
    public void addProject(@RequestBody ProjectDto project);
    @GetMapping({"/getProject/{userId}"})
    public List<ProjectDto> getProjectList(@PathVariable Integer userId);
    @DeleteMapping({"/deleteProject/{proId}"})
    public void deleteProject(@PathVariable Integer proId);

    // Applications APIs

    @PostMapping({"/addApplications"})
    public void addApplications(@RequestBody ApplicationDto application);
    @GetMapping({"/getApplicationsByUserId/{userId}"})
    public List<ApplicationDto> getApplicationsByUserId(@PathVariable Integer userId);
    @GetMapping({"/getApplicationsByJobId/{jobId}"})
    public List<ApplicationDto> getApplicationsByJobId(@PathVariable Integer jobId);
    @GetMapping({"/getApplicationsByCompId/{compId}"})
    public List<ApplicationDto> getApplicationsByCompId(@PathVariable Integer compId);
    @PatchMapping({"/updateApplicationActivity/{applicationId}"})
    public void updateApplicationActivity(@RequestBody Boolean active,@PathVariable Integer applicationId);
    @PatchMapping({"/updateApplicationStatus/{applicationId}"})
    public void updateApplicationStatus(@RequestBody String status,@PathVariable Integer applicationId);
    @PatchMapping({"/updateApplicationWithdrawStatus/{appId}"})
    public void updateApplicationWithdrawStatus(@RequestBody Boolean withdrawn,@PathVariable Integer appId);



// Job APIs

    @PostMapping({"/addJob"})
    public void addJob(@RequestBody JobDto job);
    @GetMapping("/getAllActiveJobs")
    public Page<JobDto> getAllJobs(@RequestParam(name = "page",defaultValue = "0") int page,
                                @RequestParam(name = "size",defaultValue = "4") int size,
                                @RequestParam(required = false) List<String> roles,
                                @RequestParam(required = false) List<String> locations
                                ) ;

    @GetMapping("/getAllTheJobs")
    public List<JobDto> getAllTheJobs();


    @GetMapping({"/getJobsByUserId/{userId}"})
    public List<JobDto> getAllJobsByUserId(@PathVariable Integer userId);
    @PatchMapping({"/removeJob/{jobId}"})
    public void removeJobById(@RequestBody Boolean status,@PathVariable Integer jobId);




}
