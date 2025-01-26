/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ABCFitness.Gym.service;

import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.mapper.ClassMapper;
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.repository.ClassRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yakub
 */
@Service
@Transactional
public class ClassService {
    @Autowired
    private ClassRepository classRepository;
    
    @Autowired
    private ClassMapper classMapper;
    
    public ClubClassDTO createClass(ClubClassDTO clubClassDTO) {
        ClubClass clubClass = classMapper.toEntity(clubClassDTO);
        validateClass(clubClass);
        ClubClass savedClass = classRepository.save(clubClass);
        return classMapper.toDTO(savedClass);
    }

    private void validateClass(ClubClass clubClass) {
        // Validation logic
        if (clubClass.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date must be in future");
        }
    }
}
