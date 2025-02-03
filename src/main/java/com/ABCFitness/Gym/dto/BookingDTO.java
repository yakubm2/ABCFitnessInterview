package com.ABCFitness.Gym.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    
    @NotBlank(message = "Member name is required")
    private String memberName;
    
    private Long classId;
    
    private String className;
    
    @Future(message = "Participation date must be in the future")
    private LocalDate participationDate;
}
