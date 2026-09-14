package com.example.passport.controller;

import com.example.passport.dto.LoginRequest;
import com.example.passport.dto.LoginResponse;
import com.example.passport.dto.RegisterUserRequest;
import com.example.passport.model.User;
import com.example.passport.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "Endpoints for user & officer login, registration, and session check")
public class AuthController {

    public static final String SESSION_USER = "LOGGED_IN_USER";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user or officer")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        LoginResponse response = authService.authenticate(request);
        session.setAttribute(SESSION_USER, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new applicant account")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterUserRequest request, HttpSession session) {
        User user = authService.registerApplicant(request);
        LoginResponse response = LoginResponse.fromUser(user);
        session.setAttribute(SESSION_USER, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    @Operation(summary = "Get currently authenticated user")
    public ResponseEntity<LoginResponse> getCurrentUser(HttpSession session) {
        LoginResponse user = (LoginResponse) session.getAttribute(SESSION_USER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out the current user session")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
