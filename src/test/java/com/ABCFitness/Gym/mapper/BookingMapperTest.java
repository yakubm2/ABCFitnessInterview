package com.ABCFitness.Gym.mapper;

import com.ABCFitness.Gym.dto.BookingDTO;
import com.ABCFitness.Gym.model.Booking;
import com.ABCFitness.Gym.model.ClubClass;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingMapperTest {

    private BookingMapper bookingMapper;

    @BeforeEach
    public void setUp() {
        bookingMapper = new BookingMapper();
    }

    @Test
    public void testToEntity() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(1L);
        bookingDTO.setMemberName("Yakub Md");
        bookingDTO.setParticipationDate(LocalDate.now().plusDays(1));
        bookingDTO.setClassId(2L);

        ClubClass clubClass = new ClubClass();
        clubClass.setId(2L);
        clubClass.setName("Yoga Class");

        Booking booking = bookingMapper.toEntity(bookingDTO, clubClass);

        assertNotNull(booking);
        assertEquals(bookingDTO.getId(), booking.getId());
        assertEquals(bookingDTO.getMemberName(), booking.getMemberName());
        assertEquals(bookingDTO.getParticipationDate(), booking.getParticipationDate());
        assertEquals(clubClass, booking.getClubClass());
    }

    @Test
    public void testToDTO() {
        ClubClass clubClass = new ClubClass();
        clubClass.setId(2L);
        clubClass.setName("Yoga Class");

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setMemberName("Yakub Md");
        booking.setParticipationDate(LocalDate.now().plusDays(1));
        booking.setClubClass(clubClass);

        BookingDTO bookingDTO = bookingMapper.toDTO(booking);

        assertNotNull(bookingDTO);
        assertEquals(booking.getId(), bookingDTO.getId());
        assertEquals(booking.getMemberName(), bookingDTO.getMemberName());
        assertEquals(booking.getParticipationDate(), bookingDTO.getParticipationDate());
        assertEquals(clubClass.getId(), bookingDTO.getClassId());
        assertEquals(clubClass.getName(), bookingDTO.getClassName());
    }
}

