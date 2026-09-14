package com.example.passport.repository;

import com.example.passport.model.PassportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassportRecordRepository extends JpaRepository<PassportRecord, Long> {
    Optional<PassportRecord> findByPassportNumber(String passportNumber);
}
