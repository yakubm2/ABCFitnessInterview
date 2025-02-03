package com.ABCFitness.Gym.mapper;

import com.ABCFitness.Gym.dto.BookingDTO;
import com.ABCFitness.Gym.model.Booking;
import com.ABCFitness.Gym.model.ClubClass;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Booking toEntity(BookingDTO dto, ClubClass clubClass) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setMemberName(dto.getMemberName());
        booking.setClubClass(clubClass);
        booking.setParticipationDate(dto.getParticipationDate());
        return booking;
    }

    public BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setMemberName(booking.getMemberName());
        dto.setClassId(booking.getClubClass().getId());
        dto.setClassName(booking.getClubClass().getName());
        dto.setParticipationDate(booking.getParticipationDate());
        return dto;
    } 
}
