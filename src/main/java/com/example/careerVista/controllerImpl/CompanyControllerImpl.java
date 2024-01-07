package com.example.careerVista.controllerImpl;

import com.example.careerVista.controller.CompanyController;
import com.example.careerVista.dto.CompanyDto;
import com.example.careerVista.dto.JobDto;
import com.example.careerVista.dto.UserDto;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import com.example.careerVista.service.CompanyService;
import com.example.careerVista.utils.CareerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CompanyControllerImpl implements CompanyController {

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try
        {
            return companyService.signUp(requestMap);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return CareerUtils.getResponseEntity("Failed to Signup", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try
        {
            return companyService.logIn(requestMap);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return CareerUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @Override
    public boolean getCompanyViaUsername(String username) {
        return companyService.getCompanyViaUsername(username);
    }

    @Override
    public CompanyDto getCompanyByUsername(String username) {
        return companyService.getCompanyByUsername(username);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @Override
    public void deleteCompany(Integer compId)
    {
        companyService.deleteCompany(compId);
    }

    @Override
    public List<UserDto> getEmployeesByCompanyId(Integer compId) {
        return companyService.getEmployeesByCompanyId(compId);
    }

    @Override
    public void updateCandidateStatus(String status, Integer userId)
    {
      companyService.updateCandidateStatus(status,userId);
    }

    @Override
    public List<JobDto> getJobsByCompanyId(Integer compId) {
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
