package com.ABCFitness.Gym.functional;


import com.ABCFitness.Gym.dto.BookingDTO;
import com.ABCFitness.Gym.model.Booking;
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.repository.BookingRepository;
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
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class BookingControllerFunctionalTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookingRepository bookingRepository;

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
    public void testCreateBooking_Success() throws Exception {
        ClubClass clubClass = new ClubClass();
        clubClass.setName("Yoga Class");
        clubClass.setStartDate(LocalDate.now().plusDays(1));
        clubClass.setEndDate(LocalDate.now().plusDays(2));
        clubClass.setCapacity(20);
        ClubClass savedClubClass = classRepository.save(clubClass);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setMemberName("Yakub Md");
        bookingDTO.setClassId(savedClubClass.getId());
        bookingDTO.setParticipationDate(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberName").value("Yakub Md"))
                .andExpect(jsonPath("$.classId").value(clubClass.getId().intValue()))
                .andExpect(jsonPath("$.participationDate").value(LocalDate.now().plusDays(1).toString()));
    }

    @Test
    public void testSearchBookings_Success() throws Exception {
        ClubClass clubClass = new ClubClass();
        clubClass.setName("Yoga Class");
        clubClass.setStartDate(LocalDate.now().plusDays(1));
        clubClass.setEndDate(LocalDate.now().plusDays(2));
        clubClass.setCapacity(20);
        classRepository.save(clubClass);

        Booking booking = new Booking();
        booking.setMemberName("Yakub Md");
        booking.setClubClass(clubClass);
        booking.setParticipationDate(LocalDate.now().plusDays(1));
        bookingRepository.save(booking);

        mockMvc.perform(get("/api/bookings/search")
                        .param("memberName", "Yakub Md")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(2).toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))             
                .andExpect(jsonPath("$[0].memberName").value("Yakub Md"))
                .andExpect(jsonPath("$[0].classId").value(clubClass.getId().intValue()))
                .andExpect(jsonPath("$[0].participationDate").value(LocalDate.now().plusDays(1).toString()));
    }
}

