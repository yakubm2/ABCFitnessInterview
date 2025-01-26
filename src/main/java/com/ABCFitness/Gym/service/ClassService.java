package com.ABCFitness.Gym.service;

import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.mapper.ClassMapper;
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.repository.ClassRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        validateUniqueClassPerDay(clubClass);
        ClubClass savedClass = classRepository.save(clubClass);
        return classMapper.toDTO(savedClass);
    }
    
    private void validateClass(ClubClass clubClass) {
        // Validation logic
        if (clubClass.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date must be in future");
        }
    }
    
    private void validateUniqueClassPerDay(ClubClass clubClass) {
        List<ClubClass> existingClasses = classRepository.findByStartDate(clubClass.getStartDate());
    
        if (!existingClasses.isEmpty()) {
        // If there are existing classes on this date, throw an exception
        throw new RuntimeException("A class already exists on this date");
        }
    }
}
