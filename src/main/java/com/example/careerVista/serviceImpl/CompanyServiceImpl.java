package com.example.careerVista.serviceImpl;

import com.example.careerVista.Jwt.CompanyDetailsService;
import com.example.careerVista.Jwt.JwtUtil;
import com.example.careerVista.dao.*;
import com.example.careerVista.dao.CompanyDao;
import com.example.careerVista.entity.Applications;
import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.Job;
import com.example.careerVista.entity.User;
import com.example.careerVista.service.CandidateService;
import com.example.careerVista.service.CompanyService;
import com.example.careerVista.utils.CareerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
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

    @Autowired
  private  JwtUtil jwtUtil;

    @Autowired
    private CompanyDetailsService companyDetailsService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        if(validateSignUpMap(requestMap)) {

            log.info("inside sign up", requestMap);
            Company company = companyDao.findAllByUsername(requestMap.get("username"));

            if (Objects.isNull(company)) {
                Company newCompany = getCompanyFromMap(requestMap);
                companyDao.save(newCompany);
                return CareerUtils.getResponseEntity("Successfully registered", HttpStatus.OK);
            } else {
                return CareerUtils.getResponseEntity("User Already exists", HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return CareerUtils.getResponseEntity("User Already exists",HttpStatus.BAD_REQUEST);

        }
    }

    public Company getCompanyFromMap(Map<String,String>requestMap)
    {
        Company company = new Company();
        company.setName(requestMap.get("name"));
        company.setUsername(requestMap.get("username"));
        company.setPassword(passwordEncoder.encode(requestMap.get("password")));
        company.setPhoneNumber(requestMap.get("phoneNumber"));
        company.setFoundingDate(requestMap.get("foundingDate"));
        company.setCeoName(requestMap.get("ceoName"));
        company.setRole("company");

        return company;
    }

    private Boolean validateSignUpMap(Map<String,String>requestMap)
    {
        if(requestMap.containsKey("username") &&
                requestMap.containsKey("phoneNumber") && requestMap.containsKey("password") && requestMap.containsKey("role"))
            return true;

        return false;
    }

    @Override
    public ResponseEntity<String> logIn(Map<String, String> requestMap) {

        log.info("Inside login");


        Company company = companyDao.findAllByUsername(requestMap.get("username"));


        if (company != null && passwordEncoder.matches(CharBuffer.wrap(requestMap.get("password")),company.getPassword() )) {


            return new ResponseEntity<String>("{\"token\":\"" +
                    jwtUtil.generateToken(company.getUsername(),
                            company.getRole()) + "\",\"role\":\"" + company.getRole() + "\"}", HttpStatus.OK);


//                }
//                else{
//                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval"+"\"}",HttpStatus.BAD_REQUEST );
//                }
        }


        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials" + "\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDao.findAll();
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
