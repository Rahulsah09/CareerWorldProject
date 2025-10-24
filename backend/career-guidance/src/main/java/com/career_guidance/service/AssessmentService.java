package com.career_guidance.service;



import com.career_guidance.model.Assessment;
import com.career_guidance.model.Career;
import com.career_guidance.model.User;
import com.career_guidance.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private CareerService careerService;

    public Assessment saveAssessment(Assessment assessment) {
        // Enhanced career recommendation logic with scoring system
        String recommendedCareer = getRecommendedCareer(assessment);
        assessment.setRecommendedCareer(recommendedCareer);
        return assessmentRepository.save(assessment);
    }

    public List<Assessment> getAssessmentsByUser(User user) {
        return assessmentRepository.findByUser(user);
    }

    public List<Career> getTopRecommendedCareers(Assessment assessment) {
        List<Career> allCareers = careerService.getAllCareers();
        String combinedAnswers = combineAnswers(assessment);
        
        return allCareers.stream()
            .map(career -> {
                double score = calculateCareerScore(combinedAnswers, career);
                return new CareerScore(career, score);
            })
            .sorted((a, b) -> Double.compare(b.score, a.score))
            .limit(3)
            .map(cs -> cs.career)
            .collect(Collectors.toList());
    }

    private String getRecommendedCareer(Assessment assessment) {
        List<Career> topCareers = getTopRecommendedCareers(assessment);
        return topCareers.isEmpty() ? "No specific recommendation" : topCareers.get(0).getTitle();
    }

    private String combineAnswers(Assessment assessment) {
        StringBuilder combined = new StringBuilder();
        combined.append(assessment.getQuestion1()).append(" ");
        combined.append(assessment.getQuestion2()).append(" ");
        combined.append(assessment.getQuestion3()).append(" ");
        combined.append(assessment.getQuestion4());
        return combined.toString().toLowerCase();
    }

    private double calculateCareerScore(String combinedAnswers, Career career) {
        if (career.getTags() == null || career.getTags().isEmpty()) {
            return 0.0;
        }

        String[] tags = career.getTags().toLowerCase().split(",");
        double score = 0.0;
        int totalTags = tags.length;

        for (String tag : tags) {
            tag = tag.trim();
            if (combinedAnswers.contains(tag)) {
                score += 1.0;
            }
        }

        // Calculate percentage match and add bonus for exact keyword matches
        double baseScore = (score / totalTags) * 100;
        
        // Add bonus for specific high-value keywords
        String[] highValueKeywords = {"tech", "creative", "business", "healthcare", "engineering", "science"};
        for (String keyword : highValueKeywords) {
            if (combinedAnswers.contains(keyword) && career.getTags().toLowerCase().contains(keyword)) {
                baseScore += 10.0;
            }
        }

        // Add bonus for work environment match
        if (combinedAnswers.contains("office") && career.getTags().toLowerCase().contains("business")) {
            baseScore += 5.0;
        }
        if (combinedAnswers.contains("remote") && career.getTags().toLowerCase().contains("tech")) {
            baseScore += 5.0;
        }
        if (combinedAnswers.contains("creative") && career.getTags().toLowerCase().contains("design")) {
            baseScore += 5.0;
        }

        return baseScore;
    }

    private static class CareerScore {
        final Career career;
        final double score;

        CareerScore(Career career, double score) {
            this.career = career;
            this.score = score;
        }
    }
}
