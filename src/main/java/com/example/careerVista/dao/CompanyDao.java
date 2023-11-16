package com.example.careerVista.dao;

import com.example.careerVista.entity.Company;
import com.example.careerVista.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDao extends JpaRepository<Company,Integer>
{
    Company findAllByUsername(String username);
}
