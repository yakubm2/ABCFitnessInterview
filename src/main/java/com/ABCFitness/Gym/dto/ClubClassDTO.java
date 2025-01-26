/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ABCFitness.Gym.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author yakub
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubClassDTO {
    private Long id;
    
    @NotBlank(message = "Class name is required")
    private String name;
    
    @Future(message = "Start date must be in the future")
    private LocalDate startDate;
    
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    private LocalTime startTime;
    
    @Positive(message = "Duration must be positive")
    private int duration;
    
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
}

