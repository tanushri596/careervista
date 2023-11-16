package com.example.careerVista.Jwt;

import com.example.careerVista.entity.Company;
import com.example.careerVista.dao.CompanyDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyDetailsService implements UserDetailsService
{

    @Autowired
    private final CompanyDao companyDao;


    private Company compDetail;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}",username);
        compDetail = companyDao.findAllByUsername(username);
        log.info("inside loadUserByUsername {}",compDetail);
        if (!Objects.isNull(compDetail))
            return new org.springframework.security.core.userdetails.User(compDetail.getUsername(), compDetail.getPassword()
                    , new ArrayList<>());//The User used is pre-built one.userDetail is the database one.
        else throw new UsernameNotFoundException("User not found");
    }

    public Company getUserDetail() {
        return compDetail;
    }
}
