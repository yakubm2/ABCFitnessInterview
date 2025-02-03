package com.ABCFitness.Gym.controller;

import com.ABCFitness.Gym.dto.ClubClassDTO;
import com.ABCFitness.Gym.service.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping("/createClass")
    public ResponseEntity<ClubClassDTO> createClass(@Valid @RequestBody ClubClassDTO clubClass) {
        return ResponseEntity.ok(classService.createClass(clubClass));
    }
}
