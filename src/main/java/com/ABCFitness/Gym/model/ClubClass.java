package com.ABCFitness.Gym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Id;  
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;




@Entity
@Table(name = "club_classes") 
@Data
public class ClubClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private int version;
    
    @NotBlank
    private String name;
    
    @Future
    private LocalDate startDate;
    
    @Future
    private LocalDate endDate;
    
    private LocalTime startTime;
    
    private int duration;
    
    @Min(1)
    private int capacity;
}
