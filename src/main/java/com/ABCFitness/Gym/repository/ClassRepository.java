/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ABCFitness.Gym.repository;

import com.ABCFitness.Gym.model.ClubClass;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yakub
 */
@Repository
public interface ClassRepository extends JpaRepository<ClubClass, Long> {
    List<ClubClass> findByStartDateBetween(LocalDate start, LocalDate end);
}
