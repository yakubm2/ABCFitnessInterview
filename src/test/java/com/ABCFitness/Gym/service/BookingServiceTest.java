package com.ABCFitness.Gym.service;

import com.ABCFitness.Gym.dto.BookingDTO;
import com.ABCFitness.Gym.exception.CapacityExceededException;
import com.ABCFitness.Gym.mapper.BookingMapper;
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.model.Booking;
import com.ABCFitness.Gym.repository.BookingRepository;
import com.ABCFitness.Gym.repository.ClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    private BookingDTO bookingDTO;
    private ClubClass clubClass;
    private Booking booking;

    @BeforeEach
    public void setUp() {
        bookingDTO = new BookingDTO();
        bookingDTO.setClassId(1L);
        bookingDTO.setParticipationDate(LocalDate.now().plusDays(1));

        clubClass = new ClubClass();
        clubClass.setCapacity(10);

        booking = new Booking();
        booking.setClubClass(clubClass);
        booking.setParticipationDate(LocalDate.now().plusDays(1));

        when(classRepository.findById(1L)).thenReturn(Optional.of(clubClass));
        when(bookingMapper.toEntity(bookingDTO, clubClass)).thenReturn(booking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingDTO);
    }

    @Test
    public void testCreateBooking_Success() {
        when(bookingRepository.countByClubClassAndParticipationDate(any(ClubClass.class), any(LocalDate.class)))
                .thenReturn(0);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingDTO result = bookingService.createBooking(bookingDTO);

        assertNotNull(result);
        assertEquals(bookingDTO.getParticipationDate(), result.getParticipationDate());
    }

    @Test
    public void testCreateBooking_ClassNotFound() {
        when(classRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookingService.createBooking(bookingDTO);
        });

        assertEquals("Class not found", exception.getMessage());
    }

    @Test
    public void testCreateBooking_ClassCapacityExceeded() {
        when(bookingRepository.countByClubClassAndParticipationDate(any(ClubClass.class), any(LocalDate.class)))
                .thenReturn(10);

        CapacityExceededException exception = assertThrows(CapacityExceededException.class, () -> {
            bookingService.createBooking(bookingDTO);
        });

        assertEquals("Class is full", exception.getMessage());
    }

    @Test
    public void testSearchBookings_ByMemberNameAndDateRange() {
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findByMemberNameAndParticipationDateBetween(anyString(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(bookings);

        List<BookingDTO> results = bookingService.searchBookings("Yakub Md", LocalDate.now(), LocalDate.now().plusDays(1));

        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchBookings_ByMemberName() {
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findByMemberName(anyString())).thenReturn(bookings);

        List<BookingDTO> results = bookingService.searchBookings("Yakub", null, null);

        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchBookings_ByDateRange() {
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findByParticipationDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(bookings);

        List<BookingDTO> results = bookingService.searchBookings(null, LocalDate.now(), LocalDate.now().plusDays(1));

        assertNotNull(results);
        assertEquals(1, results.size());
    }
}
