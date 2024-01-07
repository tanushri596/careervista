package com.example.careerVista.Jwt;

import com.example.careerVista.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.example.careerVista.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j

public class CandidateDetailsService implements UserDetailsService {


    @Autowired
    private  UserDao userDao;

    private User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // log.info("Inside loadUserByUsername {}",username);
        userDetail = userDao.findAllByUsername(username);
      //  log.info("inside loadUserByUsername {}",userDetail);
        if (!Objects.isNull(userDetail))
            return new org.springframework.security.core.userdetails.User(userDetail.getUsername(), userDetail.getPassword()
                    , new ArrayList<>());//The User used is pre-built one.userDetail is the database one.
        else throw new UsernameNotFoundException("User not found");
    }

    public User getUserDetail() {
        return userDetail;
    }

    }


