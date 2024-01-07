package com.example.careerVista.serviceImpl;

import com.example.careerVista.Jwt.CandidateDetailsService;
import com.example.careerVista.Jwt.JwtFilter;
import com.example.careerVista.Jwt.JwtUtil;
import com.example.careerVista.dao.*;
import com.example.careerVista.dto.*;
import com.example.careerVista.entity.*;
import com.example.careerVista.service.CandidateService;
import com.example.careerVista.utils.CareerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private EducationDao educationDao;

    @Autowired
    private ExperienceDao experienceDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ApplicationsDao applicationsDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CandidateDetailsService candidateDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;


    // Users


    @Override
    public ResponseEntity<String> signUp(UserSignDto requestMap) {
        if (validateSignUpMap(requestMap)) {
            log.info("inside sign up", requestMap);
            User user = userDao.findAllByUsername(requestMap.getUsername());

            if (Objects.isNull(user)) {
                User newUser = getUserFromMap(requestMap);
                userDao.save(newUser);
                return CareerUtils.getResponseEntity("Successfully registered", HttpStatus.OK);
            } else {
                return CareerUtils.getResponseEntity("Email Already exists", HttpStatus.BAD_REQUEST);
            }

        } else
            return CareerUtils.getResponseEntity("Invalid User Information entered", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> logIn(UserLogDto requestMap) {

        log.info("Inside login");


        User user = userDao.findAllByUsername(requestMap.getUsername());


        if (user != null && passwordEncoder.matches(CharBuffer.wrap( requestMap.getPassword()),user.getPassword())) {


            return new ResponseEntity<String>("{\"token\":\"" +
                    jwtUtil.generateToken(user.getUsername(),
                            user.getRole()) + "\",\"role\":\"" + user.getRole() + "\"}", HttpStatus.OK);


//                }
//                else{
//                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval"+"\"}",HttpStatus.BAD_REQUEST );
//                }
        }


        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials" + "\"}", HttpStatus.BAD_REQUEST);

    }

    @Override
    public UserDto getCandidate(String username) {

        User user = userDao.findAllByUsername(username);
         UserDto userDto;
        if(user.getCompany()!=null)
         userDto = new UserDto(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getRole(),user.getStatus(),user.getBirthDate(),user.getDesignation(),user.getCompany().getId()) ;
        else
            userDto = new UserDto(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getRole(),user.getStatus(),user.getBirthDate(),user.getDesignation(),0) ;

        return userDto;
    }

    public User getUserFromMap(UserSignDto requestMap) {
        User user = new User();
        user.setFirstName(requestMap.getFirstName());
        user.setLastName(requestMap.getLastName());
        user.setUsername(requestMap.getUsername());
        user.setPassword(passwordEncoder.encode(requestMap.getPassword()));
        user.setPhoneNumber(requestMap.getPhoneNumber());
        user.setCompany(requestMap.getCompany());
        user.setBirthDate(requestMap.getBirthDate());
        user.setDesignation(requestMap.getDesignation());
        user.setRole("candidate");
        user.setStatus(requestMap.getStatus());

        return user;
    }

    private Boolean validateSignUpMap(UserSignDto requestMap) {
        if (requestMap.getUsername() != null &&
                requestMap.getPhoneNumber() !=null && requestMap.getPassword()!=null && requestMap.getRole()!=null)
            return true;

        return false;
    }

    @Override
    public boolean getCandidateViaUsername(String username) {
        User user = userDao.findAllByUsername(username);

        if(user == null)
            return false;
        else return true;
    }
    @Override
    public void updateCandidate(UserSignDto requestBody)
    {
        User user = userDao.findAllByUsername(requestBody.getUsername());

        if (user!=null) {

            user.setBirthDate(requestBody.getBirthDate());
            user.setPhoneNumber(requestBody.getPhoneNumber());
            userDao.save(user);
        }
    }
    @Override
   public void deleteCandidate(Integer userId) {
        Optional<User> candidate = userDao.findById(userId);

        if (candidate.isPresent()) {
           // List<Job> userJobs = getAllJobsByUserId(userId);
            User user = candidate.get();
            Company company = user.getCompany();

//            for (Job job : userJobs) {
//
//
//                job.setUser(null);
//                if (company == null)
//                    jobDao.delete(job);
//            }

            List<Applications> userApplications = applicationsDao.findAllByUserId(userId);
            for (Applications application : userApplications) {
                application.setUser(null);
                applicationsDao.delete(application);
            }

            List<Education> userEducation = educationDao.findAllByUserId(userId);
            for (Education education : userEducation) {
                education.setUser(null);
                educationDao.delete(education);
            }

            List<Experience> userExperience = experienceDao.findAllByUserId(userId);
            for (Experience experience : userExperience) {
                experience.setUser(null);
                experienceDao.delete(experience);
            }

            List<Project> userProject = projectDao.findAllByUserId(userId);
            for (Project project : userProject) {
                project.setUser(null);
                projectDao.delete(project);
            }
            userDao.deleteById(userId);
        }
    }

//    Education

    @Override
    public void addEducation(EducationDto education) {
        Education education1 = new Education();
        Optional<User> user = userDao.findById(education.getUserId());
        education1.setCourseName(education.getCourseName());
        education1.setMarks(education.getMarks());
        education1.setStartDate(education.getStartDate());
        education1.setEndDate(education.getEndDate());
        education1.setInstitution(education.getInstitution());
        education1.setUser(user.get());
        System.out.println("In service education" + education);
        educationDao.save(education1);
    }


    @Override
    public List<EducationDto> getEducationByUserId(Integer userId) {
        List<EducationDto> educations = educationDao.findByUserId(userId);


        return educations;
    }

    @Override
    public void deleteEducationById(Integer eduId) {
        Optional<Education> education = educationDao.findById(eduId);

        if (education.isPresent()) {
            educationDao.deleteById(eduId);
        }


    }
    //Experience

    @Override
    public void addExperience(ExperienceDto experience) {
        Experience experience1 = new Experience();
        Optional<User> user = userDao.findById(experience.getUserId());

        experience1.setCompany(experience.getCompany());
        experience1.setDescription(experience.getDescription());
        experience1.setRole(experience.getRole());
        experience1.setStartDate(experience.getStartDate());
        experience1.setEndDate(experience.getEndDate());
        experience1.setUser(user.get());
        experienceDao.save(experience1);
    }

    @Override
    public List<ExperienceDto> getExperiencesByUserId(Integer userId)
    {
        List<ExperienceDto> experiences = experienceDao.findByUserId(userId);


        return experiences;
    }

    @Override
    public void deleteExperienceById(Integer expId) {
        Optional<Experience> experience = experienceDao.findById(expId);

        if (experience.isPresent()) {
            experienceDao.deleteById(expId);
        }
    }

    //Projects
    @Override
    public void addProject(ProjectDto project) {
        Project project1 = new Project();
        Optional<User> user = userDao.findById(project.getUserId());

        project1.setName(project.getName());
        project1.setDescription(project.getDescription());
        project1.setStartDate(project.getStartDate());
        project1.setEndDate(project.getEndDate());
        project1.setUser(user.get());

        projectDao.save(project1);
    }

    @Override
    public List<ProjectDto> getProjectsByUserId(Integer userId) {

        List<ProjectDto> projects = projectDao.findByUserId(userId);

        return projects;
    }

    public void deleteProject(Integer proId) {
        Optional<Project> project = projectDao.findById(proId);

        if (project.isPresent()) {
            projectDao.deleteById(proId);
        }
    }

    // Applications
    @Override
    public void addApplication(ApplicationDto application) {

        Applications application1 = new Applications();
     Optional<User> user = userDao.findById(application.getUserDto().getId());
          Optional<Job> job = jobDao.findById(application.getJobDto().getId());
     Optional<Company> company = companyDao.findById(application.getCompanyId());
      application1.setId(application.getId());
     application1.setRole(application.getRole());
     application1.setStatus(application.getStatus());
     application1.setActive(application.getActive());
     application1.setWithdrawn(application.getWithdrawn());
     application1.setCompanyName(application.getCompanyName());
     application1.setApplyDate(application.getApplyDate());
     if(user!=null)
     application1.setUser(user.get());

     if(job!=null)
         application1.setJob(job.get());

        if(company!=null)
            application1.setCompany(company.get());

     applicationsDao.save(application1);

    }

    @Override
    public List<ApplicationDto> getApplicationsByUserId(Integer userId) {
        List<Applications> applications = applicationsDao.findAllByUserId(userId);

        List<ApplicationDto> applicationDtos = new ArrayList<>();
        applications.forEach(
                application ->
                        applicationDtos.add(new ApplicationDto(
                        application.getId(),
                        application.getRole(),
                        application.getStatus(),
                        application.getActive(),
                        application.getWithdrawn(),
                        application.getApplyDate(),

                        new JobDto(
                                application.getJob().getId(),
                                application.getJob().getRole(),
                                application.getJob().getDescription(),
                                application.getJob().getVacancy(),
                                application.getJob().getSalary(),
                                application.getJob().getLocation(),
                                application.getJob().getPostDate(),
                                application.getJob().getStatus(),
                                application.getJob().getUser().getId(),
                                application.getJob().getCompany().getId(),
                                application.getJob().getCompany().getName()
                        ),
                        application.getCompany().getId(),
                        application.getCompanyName(),
                        new UserApplicationDto(
                                application.getUser().getId(),
                                application.getUser().getFirstName(),
                                application.getUser().getLastName()

                        )


                )));

                return applicationDtos;
    }


    @Override
    public List<ApplicationDto> getApplicationsByJobId(Integer jobId) {

        List<ApplicationDto> applications = applicationsDao.findByJobId(jobId);



        return applications;
    }

    @Override
    public void updateApplicationActivity(Boolean active, Integer applicationId) {
        Optional<Applications> userApplication = applicationsDao.findById(applicationId);

        if (userApplication.isPresent()) {
            Applications application = userApplication.get();
            application.setActive(active);
            applicationsDao.save(application);
        }
    }

    @Override
    public void updateApplicationStatus(String status, Integer applicationId) {
        Optional<Applications> userApplication = applicationsDao.findById(applicationId);

        if (userApplication.isPresent()) {
            Applications application = userApplication.get();
            application.setStatus(status);
            applicationsDao.save(application);
        }
    }

    // jobs
    @Override
    public void addJob(JobDto job) {

        Job job1 = new Job();
         Optional<User> user = userDao.findById(job.getUserId());
         Optional<Company> company = companyDao.findById(job.getCompanyId());

         job1.setDescription(job.getDescription());
         job1.setLocation(job.getLocation());
         job1.setRole(job.getRole());
         job1.setSalary(job.getSalary());
         job1.setStatus(job.getStatus());
         job1.setVacancy(job.getVacancy());
         job1.setPostDate(job.getPostDate());
         job1.setCompany(company.get());
         job1.setUser(user.get());
        jobDao.save(job1);

    }

    @Override
    public List<JobDto> getAllJobsByUserId(Integer userId) {

              List<JobDto> results = jobDao.findJobDetailsByUserId(userId);


            return results;


    }

    @Override
    public void removeJobById(Boolean status,Integer jobId) {
        Optional<Job> job = jobDao.findById(jobId);

        if (job.isPresent()) {
            job.get().setStatus(status);
            jobDao.save(job.get());
            List<Applications> userApplications = applicationsDao.findAllByJobId(jobId);

            for (Applications application : userApplications) {

                application.setActive(false);
                applicationsDao.save(application);

                  }


        }
    }

    @Override
    public Page<JobDto> getAllJobs(Pageable pageable, List<String> roles, List<String> locations) {
        List<Job> allJobs = jobDao.findAll();
        List<JobDto> filteredJobs = new ArrayList<>();

        if (roles != null && !roles.isEmpty()) {
            List<JobDto> filteredOnRoles = allJobs.stream()
                    .filter(job -> roles.contains(job.getRole().trim().toLowerCase()) && job.getStatus() == true)
                    .map(job -> new JobDto(
                            job.getId(),
                            job.getRole(),
                            job.getDescription(),
                            job.getVacancy(),
                            job.getSalary(),
                            job.getLocation(),
                            job.getPostDate(),
                            job.getStatus(),
                            job.getUser().getId(),  // Assuming there's a getUser() method on Job returning a User object
                            job.getCompany().getId(),
                            job.getCompany().getName()// Assuming there's a getCompany() method on Job returning a Company object
                    ))
                    .collect(Collectors.toList());


            filteredJobs.addAll(filteredOnRoles);
        }


        if (locations != null && !locations.isEmpty()) {
            List<JobDto> filteredOnLocations = allJobs.stream()
                    .filter(job -> locations.contains(job.getLocation()) && job.getStatus() == true)
                    .map(job -> new JobDto(
                            job.getId(),
                            job.getRole(),
                            job.getDescription(),
                            job.getVacancy(),
                            job.getSalary(),
                            job.getLocation(),
                            job.getPostDate(),
                            job.getStatus(),
                            job.getUser().getId(),
                            job.getCompany().getId(),
                            job.getCompany().getName()
                    ))
                    .collect(Collectors.toList());

            // Add to filteredJobs only if not already present
            filteredOnLocations.forEach(job -> {
                if (!filteredJobs.contains(job)) {
                    filteredJobs.add(new JobDto(
                            job.getId(),
                            job.getRole(),
                            job.getDescription(),
                            job.getVacancy(),
                            job.getSalary(),
                            job.getLocation(),
                            job.getPostDate(),
                            job.getStatus(),
                            job.getUserId(),
                            job.getCompanyId(),
                            job.getCompanyName()
                    ));
                }
            });
        }

        if(filteredJobs.isEmpty() && roles==null && locations==null)
        {
            allJobs.forEach(job -> {
                  if(job.getStatus()==true)
                    filteredJobs.add(new JobDto(
                            job.getId(),
                            job.getRole(),
                            job.getDescription(),
                            job.getVacancy(),
                            job.getSalary(),
                            job.getLocation(),
                            job.getPostDate(),
                            job.getStatus(),
                            job.getUser().getId(),  // Assuming there's a getUser() method on Job returning a User object
                            job.getCompany().getId(),
                            job.getCompany().getName()// Assuming there's a getCompany() method on Job returning a Company object
                    ));

            });
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredJobs.size());

        Page<JobDto> page = new PageImpl<>(filteredJobs.subList(start, end), pageable, filteredJobs.size());

        return page;

    }



    @Override
    public void deleteApplicationById(Integer appId)
    {
      applicationsDao.deleteById(appId);
    }



    @Override
    public void updateApplicationWithdrawStatus(Boolean withdrawn, Integer appId)
    {
        Optional<Applications> userApplication = applicationsDao.findById(appId);

        if (userApplication.isPresent()) {
            Applications application = userApplication.get();
            application.setWithdrawn(withdrawn);
            applicationsDao.save(application);
        }
    }



    @Override
    public List<JobDto> getAllTheJobs() {
        List<JobDto> allTheJobs = new ArrayList<>();

        List<Job> jobs = jobDao.findAll();

        jobs.forEach(job-> {
            if(job.getStatus())allTheJobs.add(new JobDto(
                    (Integer) job.getId(),
                    (String) job.getRole(),
                    (String) job.getDescription(),
                    (Integer) job.getVacancy(),
                    (Integer) job.getSalary(),
                    (String) job.getLocation(),
                    (String) job.getPostDate(),
                    (Boolean) job.getStatus(),
                    (Integer) job.getUser().getId(),
                    (Integer) job.getCompany().getId(),
                    (String) job.getCompany().getName()
            ));
                }
                );
        return allTheJobs;
    }

    @Override
    public List<ApplicationDto> getApplicationsByCompId(Integer compId) {
        List<ApplicationDto> applications = applicationsDao.findAllByCompanyId(compId);



        return applications;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }


}
