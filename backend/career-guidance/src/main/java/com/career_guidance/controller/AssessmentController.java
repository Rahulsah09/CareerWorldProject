package com.career_guidance.controller;

import com.career_guidance.model.Assessment;
import com.career_guidance.model.Career;
import com.career_guidance.model.User;
import com.career_guidance.service.AssessmentService;
import com.career_guidance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Assessment createAssessment(@RequestBody Assessment assessment) {
        // Find or create user
        User user = userService.getUserByEmail(assessment.getUser().getEmail());
        if (user == null) {
            user = userService.saveUser(assessment.getUser());
        }
        assessment.setUser(user);
        return assessmentService.saveAssessment(assessment);
    }

    @GetMapping("/user/{email}")
    public List<Assessment> getAssessmentsByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return assessmentService.getAssessmentsByUser(user);
        }
        return List.of();
    }

    @PostMapping("/recommendations")
    public List<Career> getCareerRecommendations(@RequestBody Assessment assessment) {
        return assessmentService.getTopRecommendedCareers(assessment);
    }
}