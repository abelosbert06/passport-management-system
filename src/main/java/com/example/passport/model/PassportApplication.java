package com.example.passport.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "passport_applications")
public class PassportApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PassportType passportType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private String idProofNumber;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "verification_record_id")
    private VerificationRecord verificationRecord;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "passport_record_id")
    private PassportRecord passportRecord;

    public PassportApplication() {
    }

    public PassportApplication(String trackingNumber, Applicant applicant, PassportType passportType,
                               ApplicationStatus status, String idProofNumber, LocalDateTime submittedAt) {
        this.trackingNumber = trackingNumber;
        this.applicant = applicant;
        this.passportType = passportType;
        this.status = status;
        this.idProofNumber = idProofNumber;
        this.submittedAt = submittedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
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

    public String getIdProofNumber() {
        return idProofNumber;
    }

    public void setIdProofNumber(String idProofNumber) {
        this.idProofNumber = idProofNumber;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public VerificationRecord getVerificationRecord() {
        return verificationRecord;
    }

    public void setVerificationRecord(VerificationRecord verificationRecord) {
        this.verificationRecord = verificationRecord;
    }

    public PassportRecord getPassportRecord() {
        return passportRecord;
    }

    public void setPassportRecord(PassportRecord passportRecord) {
        this.passportRecord = passportRecord;
    }
}
