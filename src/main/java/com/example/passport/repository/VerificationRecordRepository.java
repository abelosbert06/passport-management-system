package com.example.passport.repository;

import com.example.passport.model.VerificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRecordRepository extends JpaRepository<VerificationRecord, Long> {
}
