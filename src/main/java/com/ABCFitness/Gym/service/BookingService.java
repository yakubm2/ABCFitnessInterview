package com.ABCFitness.Gym.service;

import com.ABCFitness.Gym.dto.BookingDTO;
import com.ABCFitness.Gym.exception.CapacityExceededException;
import com.ABCFitness.Gym.mapper.BookingMapper;
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.model.Booking;
import com.ABCFitness.Gym.repository.BookingRepository;
import com.ABCFitness.Gym.repository.ClassRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private BookingRepository bookingRepository; 
    
    @Autowired
    private BookingMapper bookingMapper;


    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Check class capacity
        ClubClass clubClass =  classRepository.findById(bookingDTO.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        Booking booking = bookingMapper.toEntity(bookingDTO, clubClass);
        validateClassCapacity(booking);
        Booking saveBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(saveBooking);
    }


    private void validateClassCapacity(Booking booking) {
        int existingBookings = bookingRepository.countByClubClassAndParticipationDate(
            booking.getClubClass(), 
            booking.getParticipationDate()
        );
        
        if (existingBookings >= booking.getClubClass().getCapacity()) {
            throw new CapacityExceededException("Class is full");
        }
    }

    // Search methods for bookings
    public List<BookingDTO> searchBookings(String memberName, LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings;

        if (memberName != null && startDate != null && endDate != null) {
            bookings = bookingRepository.findByMemberNameAndParticipationDateBetween(memberName, startDate, endDate);
        } else if (memberName != null) {
            bookings = bookingRepository.findByMemberName(memberName);
        } else if (startDate != null && endDate != null) {
            bookings = bookingRepository.findByParticipationDateBetween(startDate, endDate);
        } else {
            return Collections.emptyList();
        }

        return bookings.stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
