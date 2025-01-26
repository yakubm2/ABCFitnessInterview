package com.ABCFitness.Gym.mapper;

import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.model.ClubClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ClassMapperTest {

    private ClassMapper classMapper;

    @BeforeEach
    public void setUp() {
        classMapper = new ClassMapper();
    }

    @Test
    public void testToEntity() {
        ClubClassDTO clubClassDTO = new ClubClassDTO();
        clubClassDTO.setId(1L);
        clubClassDTO.setName("Yoga Class");
        clubClassDTO.setStartDate(LocalDate.now().plusDays(1));
        clubClassDTO.setEndDate(LocalDate.now().plusDays(2));
        clubClassDTO.setStartTime(LocalTime.of(10, 0));
        clubClassDTO.setDuration(60);
        clubClassDTO.setCapacity(20);

        ClubClass clubClass = classMapper.toEntity(clubClassDTO);

        assertNotNull(clubClass);
        assertEquals(clubClassDTO.getId(), clubClass.getId());
        assertEquals(clubClassDTO.getName(), clubClass.getName());
        assertEquals(clubClassDTO.getStartDate(), clubClass.getStartDate());
        assertEquals(clubClassDTO.getEndDate(), clubClass.getEndDate());
        assertEquals(clubClassDTO.getStartTime(), clubClass.getStartTime());
        assertEquals(clubClassDTO.getDuration(), clubClass.getDuration());
        assertEquals(clubClassDTO.getCapacity(), clubClass.getCapacity());
    }

    @Test
    public void testToDTO() {
        ClubClass clubClass = new ClubClass();
        clubClass.setId(1L);
        clubClass.setName("Yoga Class");
        clubClass.setStartDate(LocalDate.now().plusDays(1));
        clubClass.setEndDate(LocalDate.now().plusDays(2));
        clubClass.setStartTime(LocalTime.of(10, 0));
        clubClass.setDuration(60);
        clubClass.setCapacity(20);

        ClubClassDTO clubClassDTO = classMapper.toDTO(clubClass);

        assertNotNull(clubClassDTO);
        assertEquals(clubClass.getId(), clubClassDTO.getId());
        assertEquals(clubClass.getName(), clubClassDTO.getName());
        assertEquals(clubClass.getStartDate(), clubClassDTO.getStartDate());
        assertEquals(clubClass.getEndDate(), clubClassDTO.getEndDate());
        assertEquals(clubClass.getStartTime(), clubClassDTO.getStartTime());
        assertEquals(clubClass.getDuration(), clubClassDTO.getDuration());
        assertEquals(clubClass.getCapacity(), clubClassDTO.getCapacity());
    }
}
