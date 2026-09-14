package com.example.passport.controller;

import com.example.passport.dto.VerificationRequest;
import com.example.passport.model.ApplicationStatus;
import com.example.passport.model.PassportApplication;
import com.example.passport.service.PassportApplicationService;
import com.example.passport.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/officer/applications")
@Tag(name = "Verification & Officer Controller", description = "Endpoints for officers to review, verify applications and issue passports")
public class OfficerController {

    private final PassportApplicationService applicationService;
    private final VerificationService verificationService;

    public OfficerController(PassportApplicationService applicationService, VerificationService verificationService) {
        this.applicationService = applicationService;
        this.verificationService = verificationService;
    }

    @GetMapping
    @Operation(summary = "List applications with optional status filter")
    public ResponseEntity<List<PassportApplication>> listApplications(
            @RequestParam(required = false) ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.getApplications(status));
    }

    @PostMapping("/{id}/verify")
    @Operation(summary = "Perform verification (Approve or Reject application)")
    public ResponseEntity<PassportApplication> verifyApplication(
            @PathVariable Long id,
            @Valid @RequestBody VerificationRequest request) {
        return ResponseEntity.ok(verificationService.verifyApplication(id, request));
    }

    @PostMapping("/{id}/issue")
    @Operation(summary = "Issue official passport for an approved application")
    public ResponseEntity<PassportApplication> issuePassport(@PathVariable Long id) {
        return ResponseEntity.ok(verificationService.issuePassport(id));
    }
}
