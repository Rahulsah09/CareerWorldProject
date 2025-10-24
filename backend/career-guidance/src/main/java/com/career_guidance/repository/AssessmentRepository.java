package com.career_guidance.repository;

import com.career_guidance.model.Assessment;
import com.career_guidance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByUser(User user);
}
