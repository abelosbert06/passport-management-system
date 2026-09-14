package com.example.passport.service;

import com.example.passport.dto.ApplicationSubmissionRequest;
import com.example.passport.dto.TrackingResponse;
import com.example.passport.model.Applicant;
import com.example.passport.model.ApplicationStatus;
import com.example.passport.model.PassportApplication;
import com.example.passport.repository.ApplicantRepository;
import com.example.passport.repository.PassportApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PassportApplicationService {

    private final PassportApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;

    public PassportApplicationService(PassportApplicationRepository applicationRepository,
                                      ApplicantRepository applicantRepository) {
        this.applicationRepository = applicationRepository;
        this.applicantRepository = applicantRepository;
    }

    public PassportApplication submitApplication(ApplicationSubmissionRequest request) {
        Applicant applicant = applicantRepository.findById(request.getApplicantId())
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found with ID: " + request.getApplicantId()));

        // Generate unique tracking number (e.g., PASS-2026-A1B2C)
        String trackingNumber = "PASS-" + LocalDateTime.now().getYear() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        PassportApplication application = new PassportApplication(
                trackingNumber,
                applicant,
                request.getPassportType(),
                ApplicationStatus.SUBMITTED,
                request.getIdProofNumber(),
                LocalDateTime.now()
        );

        return applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public TrackingResponse trackApplication(String trackingNumber) {
        PassportApplication app = applicationRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new IllegalArgumentException("No passport application found with tracking number: " + trackingNumber));
        return TrackingResponse.fromEntity(app);
    }

    @Transactional(readOnly = true)
    public List<PassportApplication> getApplications(ApplicationStatus status) {
        if (status != null) {
            return applicationRepository.findByStatus(status);
        }
        return applicationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PassportApplication> getApplicationsByApplicant(Long applicantId) {
        return applicationRepository.findByApplicantId(applicantId);
    }

    @Transactional(readOnly = true)
    public PassportApplication getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with ID: " + id));
    }
}
