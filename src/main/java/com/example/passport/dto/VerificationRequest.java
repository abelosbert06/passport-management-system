package com.example.passport.dto;

import com.example.passport.model.VerificationOutcome;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VerificationRequest {

    @NotBlank(message = "Officer name is required")
    private String officerName;

    @NotNull(message = "Verification outcome is required")
    private VerificationOutcome outcome;

    private String remarks;

    public VerificationRequest() {
    }

    public VerificationRequest(String officerName, VerificationOutcome outcome, String remarks) {
        this.officerName = officerName;
        this.outcome = outcome;
        this.remarks = remarks;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public VerificationOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(VerificationOutcome outcome) {
        this.outcome = outcome;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
