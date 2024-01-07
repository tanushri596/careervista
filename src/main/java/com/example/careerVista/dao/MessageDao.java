package com.example.careerVista.dao;

import com.example.careerVista.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<Message,Integer> {
    List<Message> findAllByChannel(String channel);
}
