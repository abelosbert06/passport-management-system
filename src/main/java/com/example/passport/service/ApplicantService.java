package com.example.passport.service;

import com.example.passport.dto.ApplicantRegistrationRequest;
import com.example.passport.model.Applicant;
import com.example.passport.repository.ApplicantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public Applicant registerApplicant(ApplicantRegistrationRequest request) {
        if (applicantRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("An applicant with email " + request.getEmail() + " is already registered.");
        }

        Applicant applicant = new Applicant(
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getDateOfBirth(),
                request.getAddress()
        );

        return applicantRepository.save(applicant);
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Applicant getApplicantById(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found with ID: " + id));
    }
}
