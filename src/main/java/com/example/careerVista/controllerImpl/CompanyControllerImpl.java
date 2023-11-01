package com.example.careerVista.controllerImpl;

import com.example.careerVista.controller.CompanyController;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import com.example.careerVista.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyControllerImpl implements CompanyController {

    @Autowired
    private CompanyService companyService;
    @Override
    public void createCompany(Company company)
    {
       companyService.addCompany(company);
    }

    @Override
    public void deleteCompany(Integer compId)
    {
        companyService.deleteCompany(compId);
    }

    @Override
    public List<User> getEmployeesByCompanyId(Integer compId) {
        return companyService.getEmployeesByCompanyId(compId);
    }

    @Override
    public List<Job> getJobsByCompanyId(Integer compId) {
        return companyService.getJobs(compId);
    }

    @Override
    public List<Applications> getApplicationsByJobId(Integer jobId) {
        return companyService.getApplications(jobId);
    }

    @Override
    public void updateApplicationStatusByCompany(String status, Integer applicationId) {
      companyService.updateApplicationStatusByCompany(status,applicationId);
    }
}
