package com.example.careerVista.serviceImpl;

import com.example.careerVista.dao.*;
import com.example.careerVista.dao.CompanyDao;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import com.example.careerVista.service.CandidateService;
import com.example.careerVista.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ApplicationsDao applicationsDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CandidateService candidateService;
    public void addCompany(Company company)
    {
       companyDao.save(company);
    }

    @Override
    public List<Job> getJobs(Integer compId) {
        return jobDao.findAllByCompanyId(compId);
    }

    @Override
    public List<Applications> getApplications(Integer jobId) {
        return applicationsDao.findAllByJobId(jobId);
    }

    @Override
    public List<User> getEmployeesByCompanyId(Integer compId) {
        return userDao.findAllByCompanyId(compId);
    }

    @Override
    public void deleteCompany(Integer compId)
    {
        Optional<Company> company = companyDao.findById(compId);

        if(company.isPresent())
        {
            List<User> users = userDao.findAllByCompanyId(compId);

            for (User user : users) {
                user.setCompany(null);
            }

            List<Job> jobs = jobDao.findAllByCompanyId(compId);

            for(Job job : jobs)
            {
                Integer jobId = job.getId();
                candidateService.deleteJobById(jobId);
            }

            companyDao.deleteById(compId);
        }


    }

    @Override
    public void updateApplicationStatusByCompany(String status, Integer applicationId)
    {
        Optional<Applications> userApplication = applicationsDao.findById(applicationId);

        if(userApplication.isPresent())
        {
            Applications application = userApplication.get();
            application.setStatus(status);
            applicationsDao.save(application);
        }
    }
}
