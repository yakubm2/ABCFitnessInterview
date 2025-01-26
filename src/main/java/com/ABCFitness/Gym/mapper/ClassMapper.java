/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ABCFitness.Gym.mapper;

/**
 *
 * @author yakub
 */
import com.ABCFitness.Gym.model.ClubClass;
import com.ABCFitness.Gym.dto.ClubClassDTO;
import org.springframework.stereotype.Component;

@Component
public class ClassMapper {
    public ClubClass toEntity(ClubClassDTO dto) {
        ClubClass clubClass = new ClubClass();
        clubClass.setId(dto.getId());
        clubClass.setName(dto.getName());
        clubClass.setStartDate(dto.getStartDate());
        clubClass.setEndDate(dto.getEndDate());
        clubClass.setStartTime(dto.getStartTime());
        clubClass.setDuration(dto.getDuration());
        clubClass.setCapacity(dto.getCapacity());
        return clubClass;
    }

    public ClubClassDTO toDTO(ClubClass clubClass) {
        ClubClassDTO dto = new ClubClassDTO();
        dto.setId(clubClass.getId());
        dto.setName(clubClass.getName());
        dto.setStartDate(clubClass.getStartDate());
        dto.setEndDate(clubClass.getEndDate());
        dto.setStartTime(clubClass.getStartTime());
        dto.setDuration(clubClass.getDuration());
        dto.setCapacity(clubClass.getCapacity());
        return dto;
    }
}
