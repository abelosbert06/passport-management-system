package com.example.passport.service;

import com.example.passport.dto.VerificationRequest;
import com.example.passport.model.*;
import com.example.passport.repository.PassportApplicationRepository;
import com.example.passport.repository.PassportRecordRepository;
import com.example.passport.repository.VerificationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
public class VerificationService {

    private final PassportApplicationRepository applicationRepository;
    private final VerificationRecordRepository verificationRepository;
    private final PassportRecordRepository passportRepository;

    public VerificationService(PassportApplicationRepository applicationRepository,
                               VerificationRecordRepository verificationRepository,
                               PassportRecordRepository passportRepository) {
        this.applicationRepository = applicationRepository;
        this.verificationRepository = verificationRepository;
        this.passportRepository = passportRepository;
    }

    public PassportApplication verifyApplication(Long applicationId, VerificationRequest request) {
        PassportApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with ID: " + applicationId));

        if (app.getStatus() == ApplicationStatus.ISSUED) {
            throw new IllegalStateException("Application has already been issued a passport.");
        }

        VerificationRecord record = new VerificationRecord(
                request.getOfficerName(),
                request.getOutcome(),
                request.getRemarks(),
                LocalDateTime.now()
        );

        record = verificationRepository.save(record);
        app.setVerificationRecord(record);

        if (request.getOutcome() == VerificationOutcome.PASSED) {
            app.setStatus(ApplicationStatus.APPROVED);
        } else {
            app.setStatus(ApplicationStatus.REJECTED);
        }

        return applicationRepository.save(app);
    }

    public PassportApplication issuePassport(Long applicationId) {
        PassportApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with ID: " + applicationId));

        if (app.getStatus() != ApplicationStatus.APPROVED) {
            throw new IllegalStateException("Passport can only be issued for APPROVED applications. Current status: " + app.getStatus());
        }

        // Generate Passport Number, e.g. P + 7 digits
        String passportNumber = "P" + (1000000 + new Random().nextInt(9000000));

        LocalDate issueDate = LocalDate.now();
        LocalDate expiryDate = issueDate.plusYears(10);

        PassportRecord passport = new PassportRecord(
                passportNumber,
                issueDate,
                expiryDate,
                "ACTIVE"
        );

        passport = passportRepository.save(passport);
        app.setPassportRecord(passport);
        app.setStatus(ApplicationStatus.ISSUED);

        return applicationRepository.save(app);
    }
}
