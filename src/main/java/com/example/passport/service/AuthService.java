package com.example.passport.service;

import com.example.passport.dto.LoginRequest;
import com.example.passport.dto.LoginResponse;
import com.example.passport.dto.RegisterUserRequest;
import com.example.passport.model.Applicant;
import com.example.passport.model.Role;
import com.example.passport.model.User;
import com.example.passport.repository.ApplicantRepository;
import com.example.passport.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;

    public AuthService(UserRepository userRepository, ApplicantRepository applicantRepository) {
        this.userRepository = userRepository;
        this.applicantRepository = applicantRepository;
    }

    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return LoginResponse.fromUser(user);
    }

    public User registerApplicant(RegisterUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username '" + request.getUsername() + "' is already taken.");
        }

        if (applicantRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' is already registered.");
        }

        Applicant applicant = new Applicant(
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getDateOfBirth(),
                request.getAddress()
        );
        applicant = applicantRepository.save(applicant);

        User user = new User(
                request.getUsername(),
                request.getPassword(),
                Role.APPLICANT,
                applicant,
                null
        );

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
