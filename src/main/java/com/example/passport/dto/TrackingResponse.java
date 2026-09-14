package com.example.passport.dto;

import com.example.passport.model.ApplicationStatus;
import com.example.passport.model.PassportApplication;
import com.example.passport.model.PassportType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrackingResponse {

    private String trackingNumber;
    private String applicantName;
    private String applicantEmail;
    private String applicantPhone;
    private PassportType passportType;
    private ApplicationStatus status;
    private LocalDateTime submittedAt;

    // Verification info
    private String verificationOfficer;
    private String verificationOutcome;
    private String verificationRemarks;
    private LocalDateTime verifiedAt;

    // Passport info
    private String passportNumber;
    private LocalDate passportIssueDate;
    private LocalDate passportExpiryDate;
    private String passportStatus;

    public TrackingResponse() {
    }

    public static TrackingResponse fromEntity(PassportApplication app) {
        TrackingResponse res = new TrackingResponse();
        res.setTrackingNumber(app.getTrackingNumber());
        if (app.getApplicant() != null) {
            res.setApplicantName(app.getApplicant().getFullName());
            res.setApplicantEmail(app.getApplicant().getEmail());
            res.setApplicantPhone(app.getApplicant().getPhoneNumber());
        }
        res.setPassportType(app.getPassportType());
        res.setStatus(app.getStatus());
        res.setSubmittedAt(app.getSubmittedAt());

        if (app.getVerificationRecord() != null) {
            res.setVerificationOfficer(app.getVerificationRecord().getOfficerName());
            res.setVerificationOutcome(app.getVerificationRecord().getOutcome().name());
            res.setVerificationRemarks(app.getVerificationRecord().getRemarks());
            res.setVerifiedAt(app.getVerificationRecord().getVerifiedAt());
        }

        if (app.getPassportRecord() != null) {
            res.setPassportNumber(app.getPassportRecord().getPassportNumber());
            res.setPassportIssueDate(app.getPassportRecord().getIssueDate());
            res.setPassportExpiryDate(app.getPassportRecord().getExpiryDate());
            res.setPassportStatus(app.getPassportRecord().getStatus());
        }

        return res;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public PassportType getPassportType() {
        return passportType;
    }

    public void setPassportType(PassportType passportType) {
        this.passportType = passportType;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getVerificationOfficer() {
        return verificationOfficer;
    }

    public void setVerificationOfficer(String verificationOfficer) {
        this.verificationOfficer = verificationOfficer;
    }

    public String getVerificationOutcome() {
        return verificationOutcome;
    }

    public void setVerificationOutcome(String verificationOutcome) {
        this.verificationOutcome = verificationOutcome;
    }

    public String getVerificationRemarks() {
        return verificationRemarks;
    }

    public void setVerificationRemarks(String verificationRemarks) {
        this.verificationRemarks = verificationRemarks;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(LocalDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public LocalDate getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(LocalDate passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public String getPassportStatus() {
        return passportStatus;
    }

    public void setPassportStatus(String passportStatus) {
        this.passportStatus = passportStatus;
    }
}
