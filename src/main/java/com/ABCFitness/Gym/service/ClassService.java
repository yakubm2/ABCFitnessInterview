package com.ABCFitness.Gym.service;

import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.mapper.ClassMapper;
import com.ABCFitness.Gym.model.ClubClass;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import com.ABCFitness.Gym.repository.ClubClassRepository;


@Service
public class ClassService {
    @Autowired
    private ClubClassRepository classRepository;
    
    @Autowired
    private ClassMapper classMapper;
    

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Retryable(value = OptimisticLockingFailureException.class, maxAttempts = 2)
    public ClubClassDTO createClass(ClubClassDTO clubClassDTO) {
        try {
            ClubClass clubClass = classMapper.toEntity(clubClassDTO);
            validateClass(clubClass);
            validateUniqueClassPerDay(clubClass);
            ClubClass savedClass = classRepository.save(clubClass);
            return classMapper.toDTO(savedClass);
        } catch (OptimisticLockingFailureException e) {          
            throw new RuntimeException(e); 
        }
    }
    
    private void validateClass(ClubClass clubClass) {
        // Validation logic for class dates
        if (clubClass.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date must be in future");
        }
    }
     
    @Transactional(readOnly = true)
    private void validateUniqueClassPerDay(ClubClass clubClass) {
        List<ClubClass> existingClasses = classRepository.findByStartDate(clubClass.getStartDate());
    
        if (!existingClasses.isEmpty()) {
        // If there are existing classes on this date, throwing an exception
        throw new RuntimeException("A class already exists on this date");
        }      
    }
}
