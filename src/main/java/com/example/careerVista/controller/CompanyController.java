package com.example.careerVista.controller;

import com.example.careerVista.dto.CompanyDto;
import com.example.careerVista.dto.JobDto;
import com.example.careerVista.dto.UserDto;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path="/company")
public interface CompanyController
{
//    @PostMapping(path="/signUp")
//    public ResponseEntity<String> signUp(@RequestBody Map<String,String>requestMap);

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Map<String,String> requestMap);

    @PostMapping("/logIn")
    public ResponseEntity<String> login (@RequestBody Map<String,String> requestMap);

    @GetMapping("/getAllCompanies")
    public List<Company> getAllCompanies();

    @GetMapping("/getCompanyViaUsername/{username}")
    public boolean getCompanyViaUsername(@PathVariable String username);

    @GetMapping("/getCompanyByUsername/{username}")
    public CompanyDto getCompanyByUsername(@PathVariable String username);



    @DeleteMapping({"/deleteCompany/{compId}"})
    public void deleteCompany(@PathVariable Integer compId);

    @GetMapping({"/getEmployees/{compId}"})
    public List<UserDto> getEmployeesByCompanyId(@PathVariable Integer compId);

    @PatchMapping({"/updateCandidateStatus/{userId}"})
    public void updateCandidateStatus(@RequestBody String status,@PathVariable Integer userId);

    @GetMapping({"/getJobs/{compId}"})
    public List<JobDto> getJobsByCompanyId(@PathVariable Integer compId);

    @GetMapping({"/getApplications/{jobId}"})
    public List<Applications> getApplicationsByJobId(@PathVariable Integer jobId);

    @PatchMapping({"/updateApplicationStatusByCompany/{applicationId}"})
    public void updateApplicationStatusByCompany(@RequestBody String status,@PathVariable Integer applicationId);



}
