package com.career_guidance.controller;

import com.career_guidance.model.Career;
import com.career_guidance.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/careers")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping
    public List<Career> getAllCareers() {
        return careerService.getAllCareers();
    }

    @GetMapping("/{id}")
    public Career getCareerById(@PathVariable Long id) {
        return careerService.getCareerById(id);
    }

    @PostMapping
    public Career createCareer(@RequestBody Career career) {
        return careerService.saveCareer(career);
    }

    @PostMapping("/reinitialize")
    public String reinitializeCareers() {
        careerService.reinitializeCareers();
        return "Careers reinitialized successfully! All 35 careers have been loaded.";
    }
}
