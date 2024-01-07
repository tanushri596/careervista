package com.example.careerVista.controllerImpl;

import com.example.careerVista.controller.CandidateController;
import com.example.careerVista.dao.ChatDao;
import com.example.careerVista.dao.UserDao;
import com.example.careerVista.dto.*;
import com.example.careerVista.entity.*;
import com.example.careerVista.service.CandidateService;
import com.example.careerVista.utils.CareerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class CandidateControllerImpl implements CandidateController {

    @Autowired
     private CandidateService candidateService;

    @Autowired
   private UserDao userDao;

    @Autowired
  private ChatDao chatDao;

    //user

    @Override
    public ResponseEntity<String> signUp(UserSignDto requestMap)
    {
        try
        {
            return candidateService.signUp(requestMap);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return CareerUtils.getResponseEntity("Failed to Signup", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(UserLogDto requestMap) {
        try
        {
            return candidateService.logIn(requestMap);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return CareerUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

//    @Override
//    public List<User> getAllUsers() {
//        return candidateService.getAllUsers();
//    }



    @Override
    public UserDto getCandidate(String username) {
        return candidateService.getCandidate(username);
    }

    @Override
    public void addChat(Chat chatDetails) {

        List<Chat> allChats = chatDao.findAllBySenderId(chatDetails.getSender().getId());
        List<User> receivers = new ArrayList<>();
        for (Chat chat: allChats)
        {
           receivers.add(userDao.findById(chat.getReceiver().getId()).get());
        }

        if(!receivers.contains(chatDetails.getReceiver()))
            chatDao.save(chatDetails);

    }

    @Override
    public HashSet<UserChatDto> getChats(Integer userId) {
        List<UserChatDto> allReceivers = chatDao.findBySenderId(userId);
        List<UserChatDto> allSenders = chatDao.findByReceiverId(userId);

        HashSet<UserChatDto> allChats = new HashSet<>();

        for(UserChatDto receiver : allReceivers)
        {
           allChats.add(receiver);

        }
        for(UserChatDto sender : allSenders)
        {
            allChats.add(sender);
        }

return allChats;

    }

    @Override
    public boolean getCandidateViaUsername(String username) {
        return candidateService.getCandidateViaUsername(username);
    }

    @Override
    public void updateCandidate(UserSignDto requestBody) {
         candidateService.updateCandidate(requestBody);
    }


    @Override
    public void deleteUser(Integer userId)
    {
       candidateService.deleteCandidate(userId);
    }


    // Education
    @Override
    public ResponseEntity<String> addEducation(EducationDto education) {
      candidateService.addEducation(education);
      return ResponseEntity.ok("FU");
    }

    @Override
    public List<EducationDto> getEducationList(Integer userId) {
        return candidateService.getEducationByUserId(userId);
    }

    @Override
    public void deleteEducation(Integer eduId) {
        candidateService.deleteEducationById(eduId);
    }


    //Experience
    @Override
    public void addExperience(ExperienceDto experience)
    {
       candidateService.addExperience(experience);
    }

    @Override
    public List<ExperienceDto> getExperienceList(Integer userId) {

        return candidateService.getExperiencesByUserId(userId);
    }

    @Override
    public void deleteExperience(Integer expId) {
      candidateService.deleteExperienceById(expId);
    }


    //Project
    @Override
    public void addProject(ProjectDto project)
    {
       candidateService.addProject(project);
    }

    @Override
    public List<ProjectDto> getProjectList(Integer userId) {
       return candidateService.getProjectsByUserId(userId);
    }

    @Override
    public void deleteProject(Integer proId) {
        candidateService.deleteProject(proId);
    }


    //Applications
    @Override
    public void addApplications(ApplicationDto application)
    {
       candidateService.addApplication(application);
    }

    @Override
    public List<ApplicationDto> getApplicationsByUserId(Integer userId) {

       return candidateService.getApplicationsByUserId(userId);
    }

    @Override
    public List<ApplicationDto> getApplicationsByJobId(Integer jobId) {
        return candidateService.getApplicationsByJobId(jobId);
    }

    @Override
    public List<ApplicationDto> getApplicationsByCompId(Integer compId) {
        return candidateService.getApplicationsByCompId(compId);
    }

    @Override
    public void updateApplicationActivity(Boolean active,Integer applicationId) {
     candidateService.updateApplicationActivity(active,applicationId);
    }

    @Override
    public void updateApplicationStatus(String status,Integer applicationId) {
        candidateService.updateApplicationStatus(status,applicationId);
    }

    @Override
    public void updateApplicationWithdrawStatus(Boolean withdrawn, Integer appId)
    {
        candidateService.updateApplicationWithdrawStatus(withdrawn,appId);
    }


    //Jobs

    @Override
    public void addJob(JobDto job)
    {
      candidateService.addJob(job);
    }

    @Override
    public Page<JobDto> getAllJobs(int page,int size,List<String>roles,List<String>locations) {
        Pageable pageable = PageRequest.of(page, size);

        return candidateService.getAllJobs(pageable,roles,locations);
    }

    @Override
    public List<JobDto> getAllTheJobs() {
        return candidateService.getAllTheJobs();
    }


    @Override
    public List<JobDto> getAllJobsByUserId(Integer userId) {

        return candidateService.getAllJobsByUserId(userId);
    }

    @Override
    public void removeJobById(Boolean status,Integer jobId) {
      candidateService.removeJobById(status,jobId);
    }
}
