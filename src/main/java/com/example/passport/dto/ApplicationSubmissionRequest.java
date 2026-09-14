package com.example.passport.dto;

import com.example.passport.model.PassportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApplicationSubmissionRequest {

    private Long applicantId;

    @NotNull(message = "Passport type is required")
    private PassportType passportType;

    @NotBlank(message = "ID proof number is required")
    private String idProofNumber;

    public ApplicationSubmissionRequest() {
    }

    public ApplicationSubmissionRequest(Long applicantId, PassportType passportType, String idProofNumber) {
        this.applicantId = applicantId;
        this.passportType = passportType;
        this.idProofNumber = idProofNumber;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public PassportType getPassportType() {
        return passportType;
    }

    public void setPassportType(PassportType passportType) {
        this.passportType = passportType;
    }

    public String getIdProofNumber() {
        return idProofNumber;
    }

    public void setIdProofNumber(String idProofNumber) {
        this.idProofNumber = idProofNumber;
    }
}
