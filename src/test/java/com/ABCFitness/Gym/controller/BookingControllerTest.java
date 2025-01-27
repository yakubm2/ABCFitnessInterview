package com.ABCFitness.Gym.controller;

import com.ABCFitness.Gym.dto.BookingDTO;
import com.ABCFitness.Gym.service.BookingService;
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
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateBooking_Success() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(5L);
        bookingDTO.setMemberName("Yakub");
        bookingDTO.setClassId(8L);
        bookingDTO.setParticipationDate(LocalDate.now().plusDays(1));

        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(bookingDTO);
        ResponseEntity<BookingDTO> createdBookings = bookingController.createBooking(bookingDTO);
        Assertions.assertEquals(createdBookings.getBody().getMemberName(),bookingDTO.getMemberName());  
    }

    @Test
    public void testCreateBooking_ValidationFails() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(3L);
        bookingDTO.setMemberName("Yakub Md");
        bookingDTO.setClassId(4L);
        bookingDTO.setParticipationDate(LocalDate.now().minusDays(1));  // Invalid date
        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchBookings_Success() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(10L);
        bookingDTO.setMemberName("Yakub Md");
        bookingDTO.setClassId(12L);
        bookingDTO.setParticipationDate(LocalDate.now().plusDays(1));

        List<BookingDTO> bookings = List.of(bookingDTO);
        when(bookingService.searchBookings(any(String.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(bookings);

        ResponseEntity<List<BookingDTO>> response = bookingController.searchBookings("Yakub Md", LocalDate.now(), LocalDate.now().plusDays(1));
        BookingDTO bookingDetails=response.getBody().getFirst();
        Assertions.assertEquals(bookingDetails.getMemberName(),bookingDTO.getMemberName());
    }

    @Test
    public void testSearchBookings_NoResults() throws Exception {
        lenient().when(bookingService.searchBookings(any(String.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/bookings/search")
                        .param("memberName", "Yakub Md")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }
}
