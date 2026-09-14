package com.example.passport.config;

import com.example.passport.model.*;
import com.example.passport.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ApplicantRepository applicantRepo,
                                     PassportApplicationRepository applicationRepo,
                                     VerificationRecordRepository verificationRepo,
                                     PassportRecordRepository passportRepo,
                                     UserRepository userRepo) {
        return args -> {
            // Applicant 1: John Doe (Pending submission)
            Applicant john = new Applicant("John Doe", "john.doe@example.com", "+1-555-0101",
                    LocalDate.of(1995, 5, 20), "123 Maple Street, Springfield");
            john = applicantRepo.save(john);

            User userJohn = new User("john", "password123", Role.APPLICANT, john, null);
            userRepo.save(userJohn);

            PassportApplication app1 = new PassportApplication("PASS-2026-DEMO01", john,
                    PassportType.REGULAR, ApplicationStatus.SUBMITTED, "ID-987654321", LocalDateTime.now().minusDays(2));
            applicationRepo.save(app1);

            // Applicant 2: Jane Smith (Approved, ready to issue)
            Applicant jane = new Applicant("Jane Smith", "jane.smith@example.com", "+1-555-0102",
                    LocalDate.of(1998, 8, 14), "456 Oak Avenue, Metropolis");
            jane = applicantRepo.save(jane);

            User userJane = new User("jane", "password123", Role.APPLICANT, jane, null);
            userRepo.save(userJane);

            VerificationRecord vRecord = new VerificationRecord("Officer Davis", VerificationOutcome.PASSED,
                    "All original documents verified and background check clear.", LocalDateTime.now().minusDays(1));
            vRecord = verificationRepo.save(vRecord);

            PassportApplication app2 = new PassportApplication("PASS-2026-DEMO02", jane,
                    PassportType.TATKAAL, ApplicationStatus.APPROVED, "ID-123456789", LocalDateTime.now().minusDays(4));
            app2.setVerificationRecord(vRecord);
            applicationRepo.save(app2);

            // Applicant 3: Robert Brown (Already Issued)
            Applicant robert = new Applicant("Robert Brown", "robert.brown@example.com", "+1-555-0103",
                    LocalDate.of(1989, 11, 3), "789 Pine Road, Gotham");
            robert = applicantRepo.save(robert);

            User userRobert = new User("robert", "password123", Role.APPLICANT, robert, null);
            userRepo.save(userRobert);

            VerificationRecord vRecord2 = new VerificationRecord("Officer Clark", VerificationOutcome.PASSED,
                    "Verified successfully.", LocalDateTime.now().minusDays(10));
            vRecord2 = verificationRepo.save(vRecord2);

            PassportRecord pRecord = new PassportRecord("P8492015", LocalDate.now().minusDays(9),
                    LocalDate.now().minusDays(9).plusYears(10), "ACTIVE");
            pRecord = passportRepo.save(pRecord);

            PassportApplication app3 = new PassportApplication("PASS-2026-DEMO03", robert,
                    PassportType.REGULAR, ApplicationStatus.ISSUED, "ID-456789123", LocalDateTime.now().minusDays(12));
            app3.setVerificationRecord(vRecord2);
            app3.setPassportRecord(pRecord);
            applicationRepo.save(app3);

            // Verification Officers
            User officer1 = new User("officer_davis", "officer123", Role.OFFICER, null, "Officer Davis");
            userRepo.save(officer1);

            User officer2 = new User("officer_clark", "officer123", Role.OFFICER, null, "Officer Clark");
            userRepo.save(officer2);
        };
    }
}
