package com.ABCFitness.Gym.functional;


import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.repository.ClassRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class ClassControllerFunctionalTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClassRepository classRepository;

    private MockMvc mockMvc;
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
        clubClassDTO.setName("Yoga Class");
        clubClassDTO.setStartDate(LocalDate.now().plusDays(1));
        clubClassDTO.setEndDate(LocalDate.now().plusDays(2));
        clubClassDTO.setStartTime(LocalTime.of(10, 0));
        clubClassDTO.setDuration(60);
        clubClassDTO.setCapacity(20);

        mockMvc.perform(post("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clubClassDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Yoga Class"))
                .andExpect(jsonPath("$.startDate").value(LocalDate.now().plusDays(1).toString()))
                .andExpect(jsonPath("$.endDate").value(LocalDate.now().plusDays(2).toString()))
                .andExpect(jsonPath("$.startTime").value(LocalTime.of(10, 0).toString() + ":00"))
                .andExpect(jsonPath("$.duration").value(60))
                .andExpect(jsonPath("$.capacity").value(20));
    }
}

