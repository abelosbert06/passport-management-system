package com.example.passport.controller;

import com.example.passport.dto.ApplicationSubmissionRequest;
import com.example.passport.dto.LoginResponse;
import com.example.passport.dto.TrackingResponse;
import com.example.passport.model.PassportApplication;
import com.example.passport.model.Role;
import com.example.passport.service.PassportApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@Tag(name = "Passport Application Controller", description = "Endpoints for submitting and tracking passport applications")
public class PassportApplicationController {

    private final PassportApplicationService applicationService;

    public PassportApplicationController(PassportApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @Operation(summary = "Submit a new passport application")
    public ResponseEntity<PassportApplication> submitApplication(
            @Valid @RequestBody ApplicationSubmissionRequest request,
            HttpSession session) {

        LoginResponse user = (LoginResponse) session.getAttribute(AuthController.SESSION_USER);
        if (user != null && user.getRole() == Role.APPLICANT && user.getApplicantId() != null) {
            request.setApplicantId(user.getApplicantId());
        }

        if (request.getApplicantId() == null) {
            throw new IllegalArgumentException("Applicant ID is required or user must be logged in as Applicant.");
        }

        PassportApplication application = applicationService.submitApplication(request);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    @Operation(summary = "List all applications submitted by currently logged-in applicant")
    public ResponseEntity<List<PassportApplication>> getMyApplications(HttpSession session) {
        LoginResponse user = (LoginResponse) session.getAttribute(AuthController.SESSION_USER);
        if (user == null || user.getRole() != Role.APPLICANT || user.getApplicantId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(applicationService.getApplicationsByApplicant(user.getApplicantId()));
    }

    @GetMapping("/track/{trackingNumber}")
    @Operation(summary = "Track passport application status online by tracking number")
    public ResponseEntity<TrackingResponse> trackApplication(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(applicationService.trackApplication(trackingNumber));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get application details by ID")
    public ResponseEntity<PassportApplication> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }
}
