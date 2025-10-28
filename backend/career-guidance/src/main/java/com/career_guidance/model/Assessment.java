package com.career_guidance.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assessments")

public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String question1; // Interest in technology
    private String question2; // Preferred work environment
    private String question3; // Salary expectation
    private String question4; // Education level
    private String recommendedCareer;
}
