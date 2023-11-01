package com.example.careerVista.controller;

import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public interface CompanyController
{
    @PostMapping({"/createCompany"})
    public void createCompany(@RequestBody Company company);

    @GetMapping({"/getJobs/{compId}"})
    public List<Job> getJobs(@PathVariable Integer compId);

    @GetMapping({"/getApplications/{jobId}"})
    public List<Applications> getApplications(@PathVariable Integer jobId);


}
