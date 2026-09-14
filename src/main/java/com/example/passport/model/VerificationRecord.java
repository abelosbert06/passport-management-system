package com.example.passport.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_records")
public class VerificationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String officerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationOutcome outcome;

    @Column(length = 1000)
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime verifiedAt;

    public VerificationRecord() {
    }

    public VerificationRecord(String officerName, VerificationOutcome outcome, String remarks, LocalDateTime verifiedAt) {
        this.officerName = officerName;
        this.outcome = outcome;
        this.remarks = remarks;
        this.verifiedAt = verifiedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
}
