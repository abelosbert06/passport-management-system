package com.example.passport.repository;

import com.example.passport.model.ApplicationStatus;
import com.example.passport.model.PassportApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassportApplicationRepository extends JpaRepository<PassportApplication, Long> {
    Optional<PassportApplication> findByTrackingNumber(String trackingNumber);
    List<PassportApplication> findByStatus(ApplicationStatus status);
    List<PassportApplication> findByApplicantId(Long applicantId);
}
