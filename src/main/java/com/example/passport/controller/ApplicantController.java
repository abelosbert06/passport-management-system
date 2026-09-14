package com.example.passport.controller;

import com.example.passport.dto.ApplicantRegistrationRequest;
import com.example.passport.model.Applicant;
import com.example.passport.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applicants")
@Tag(name = "Applicant Controller", description = "Endpoints for applicant registration and profile management")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    @Operation(summary = "Register a new applicant")
    public ResponseEntity<Applicant> registerApplicant(@Valid @RequestBody ApplicantRegistrationRequest request) {
        Applicant applicant = applicantService.registerApplicant(request);
        return new ResponseEntity<>(applicant, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List all registered applicants")
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        return ResponseEntity.ok(applicantService.getAllApplicants());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get applicant by ID")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable Long id) {
        return ResponseEntity.ok(applicantService.getApplicantById(id));
    }
}
