package com.example.passport;

import com.example.passport.dto.*;
import com.example.passport.model.Applicant;
import com.example.passport.model.ApplicationStatus;
import com.example.passport.model.PassportType;
import com.example.passport.model.Role;
import com.example.passport.model.User;
import com.example.passport.model.VerificationOutcome;
import com.example.passport.service.ApplicantService;
import com.example.passport.service.AuthService;
import com.example.passport.service.PassportApplicationService;
import com.example.passport.service.VerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassportApplicationTests {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private PassportApplicationService applicationService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private AuthService authService;

    @Test
    void testCompletePassportLifecycle() {
        // 1. Register applicant
        ApplicantRegistrationRequest reg = new ApplicantRegistrationRequest();
        reg.setFullName("Mark Spencer");
        reg.setEmail("mark.spencer@test.com");
        reg.setPhoneNumber("+1-555-8888");
        reg.setDateOfBirth(LocalDate.of(1992, 3, 15));
        reg.setAddress("444 Elm St");

        Applicant applicant = applicantService.registerApplicant(reg);
        assertNotNull(applicant.getId());

        // 2. Submit passport application
        ApplicationSubmissionRequest sub = new ApplicationSubmissionRequest(
                applicant.getId(),
                PassportType.REGULAR,
                "DOC-998877"
        );
        com.example.passport.model.PassportApplication app = applicationService.submitApplication(sub);
        assertNotNull(app.getId());
        assertNotNull(app.getTrackingNumber());
        assertEquals(ApplicationStatus.SUBMITTED, app.getStatus());

        // 3. Officer verification -> APPROVE
        VerificationRequest vReq = new VerificationRequest("Officer Bradley", VerificationOutcome.PASSED, "Identity verified");
        com.example.passport.model.PassportApplication approvedApp = verificationService.verifyApplication(app.getId(), vReq);
        assertEquals(ApplicationStatus.APPROVED, approvedApp.getStatus());
        assertNotNull(approvedApp.getVerificationRecord());

        // 4. Officer issue passport
        com.example.passport.model.PassportApplication issuedApp = verificationService.issuePassport(approvedApp.getId());
        assertEquals(ApplicationStatus.ISSUED, issuedApp.getStatus());
        assertNotNull(issuedApp.getPassportRecord());
        assertTrue(issuedApp.getPassportRecord().getPassportNumber().startsWith("P"));
    }

    @Test
    void testAuthenticationAndRoleSegregation() {
        // Test Officer Login
        LoginResponse officerLogin = authService.authenticate(new LoginRequest("officer_davis", "officer123"));
        assertEquals(Role.OFFICER, officerLogin.getRole());
        assertEquals("Officer Davis", officerLogin.getDisplayName());

        // Test Applicant Login
        LoginResponse applicantLogin = authService.authenticate(new LoginRequest("john", "password123"));
        assertEquals(Role.APPLICANT, applicantLogin.getRole());
        assertEquals("John Doe", applicantLogin.getDisplayName());
        assertNotNull(applicantLogin.getApplicantId());

        // Test Invalid Login
        assertThrows(IllegalArgumentException.class, () ->
                authService.authenticate(new LoginRequest("john", "wrongpass")));

        // Test Register New Applicant
        RegisterUserRequest newReq = new RegisterUserRequest();
        newReq.setUsername("sam_applicant");
        newReq.setPassword("secret123");
        newReq.setFullName("Sam Wilson");
        newReq.setEmail("sam.wilson@example.com");
        newReq.setPhoneNumber("+1-555-4321");
        newReq.setDateOfBirth(LocalDate.of(1990, 1, 1));
        newReq.setAddress("100 River Road");

        User registered = authService.registerApplicant(newReq);
        assertNotNull(registered.getId());
        assertEquals(Role.APPLICANT, registered.getRole());
        assertNotNull(registered.getApplicant());
        assertEquals("Sam Wilson", registered.getApplicant().getFullName());
    }
}
