package com.example.careerVista.controllerImpl;

import com.example.careerVista.controller.CompanyController;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompanyControllerImpl implements CompanyController {

    @Autowired
    private CompanyService companyService;
    @Override
    public void createCompany(Company company)
    {
       companyService.addCompany(company);
    }

    @Override
    public List<Job> getJobs(Integer compId) {
        return companyService.getJobs(compId);
    }

    @Override
    public List<Applications> getApplications(Integer jobId) {
        return companyService.getApplications(jobId);
    }
}
