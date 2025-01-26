package com.ABCFitness.Gym.service;


import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.mapper.ClassMapper;
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.repository.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClassServiceTest {

    @InjectMocks
    private ClassService classService;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private ClassMapper classMapper;

    private ClubClassDTO clubClassDTO;
    private ClubClass clubClass;

   @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        clubClassDTO = new ClubClassDTO();
        clubClassDTO.setStartDate(LocalDate.now().plusDays(1));
        clubClassDTO.setEndDate(LocalDate.now().plusDays(2));

        clubClass = new ClubClass();
        clubClass.setStartDate(LocalDate.now().plusDays(1));
        clubClass.setEndDate(LocalDate.now().plusDays(2));

        lenient().when(classMapper.toEntity(clubClassDTO)).thenReturn(clubClass);
        lenient().when(classMapper.toDTO(any(ClubClass.class))).thenReturn(clubClassDTO);
}

    @Test
    public void testCreateClass_Success() {
        when(classRepository.save(any(ClubClass.class))).thenReturn(clubClass);
        when(classRepository.findByStartDate(any(LocalDate.class))).thenReturn(Collections.emptyList());

        ClubClassDTO result = classService.createClass(clubClassDTO);

        assertNotNull(result);
        assertEquals(clubClassDTO.getStartDate(), result.getStartDate());
        assertEquals(clubClassDTO.getEndDate(), result.getEndDate());
    }

    @Test
    public void testCreateClass_ValidationFails_EndDateBeforeNow() {
        clubClassDTO.setEndDate(LocalDate.now().minusDays(1));
        clubClass.setEndDate(LocalDate.now().minusDays(1));
        when(classMapper.toEntity(clubClassDTO)).thenReturn(clubClass);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            classService.createClass(clubClassDTO);
        });

        assertEquals("End date must be in future", exception.getMessage());
}

    @Test
    public void testCreateClass_ValidationFails_ClassAlreadyExistsOnDate() {
        when(classRepository.findByStartDate(any(LocalDate.class))).thenReturn(List.of(clubClass));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        classService.createClass(clubClassDTO);
        });

        assertEquals("A class already exists on this date", exception.getMessage());
    }
}
