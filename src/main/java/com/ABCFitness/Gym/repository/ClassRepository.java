package com.ABCFitness.Gym.repository;

import com.ABCFitness.Gym.model.ClubClass;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ClubClass, Long> {
    List<ClubClass> findByStartDateBetween(LocalDate start, LocalDate end);
    List<ClubClass> findByStartDate(LocalDate startDate);
}
