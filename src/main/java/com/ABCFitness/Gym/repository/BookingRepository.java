package com.ABCFitness.Gym.repository;

import com.ABCFitness.Gym.model.Booking;
import com.ABCFitness.Gym.model.ClubClass;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author yakub
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByMemberName(String memberName);
    List<Booking> findByParticipationDateBetween(LocalDate startDate, LocalDate endDate);
    List<Booking> findByMemberNameAndParticipationDateBetween(
        String memberName, LocalDate startDate, LocalDate endDate
    );
    int countByClubClassAndParticipationDate(ClubClass clubClass, LocalDate date);
}