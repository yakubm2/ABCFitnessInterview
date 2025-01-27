package com.ABCFitness.Gym.controller;

import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.service.ClassService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClassControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Mock
    private ClassService classService;

    @InjectMocks
    private ClassController classController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateClass_Success() throws Exception {
        ClubClassDTO clubClassDTO = new ClubClassDTO();
      //  clubClassDTO.setId(18L);
        clubClassDTO.setName("Yoga Class");
        clubClassDTO.setStartDate(LocalDate.now().plusDays(1));
        clubClassDTO.setEndDate(LocalDate.now().plusDays(2));
        clubClassDTO.setStartTime(LocalTime.of(10, 0));
        clubClassDTO.setDuration(60);
        clubClassDTO.setCapacity(20);

        when(classService.createClass(any(ClubClassDTO.class))).thenReturn(clubClassDTO);
        ResponseEntity<ClubClassDTO> response = classController.createClass(clubClassDTO);
        Assertions.assertEquals(response.getBody().getName(),clubClassDTO.getName());
    }

    @Test
    public void testCreateClass_ValidationFails() throws Exception {
        ClubClassDTO clubClassDTO = new ClubClassDTO();
        clubClassDTO.setId(1L);
        clubClassDTO.setName("Yoga Class");
        clubClassDTO.setStartDate(LocalDate.now().minusDays(1));  // Invalid date
        clubClassDTO.setEndDate(LocalDate.now().minusDays(2));    // Invalid date
        clubClassDTO.setStartTime(LocalTime.of(10, 0));
        clubClassDTO.setDuration(60);
        clubClassDTO.setCapacity(20);

        mockMvc.perform(post("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clubClassDTO)))
                .andExpect(status().isBadRequest());
    }
}
