package com.example.careerVista.serviceImpl;

import com.example.careerVista.dao.ApplicationsDao;
import com.example.careerVista.dao.CompanyDao;
import com.example.careerVista.dao.CompanyDao;
import com.example.careerVista.dao.JobDao;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ApplicationsDao applicationsDao;

    @Autowired
    private JobDao jobDao;
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
}
