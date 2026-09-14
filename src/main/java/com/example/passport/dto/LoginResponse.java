package com.example.passport.dto;

import com.example.passport.model.Role;
import com.example.passport.model.User;

public class LoginResponse {

    private Long id;
    private String username;
    private Role role;
    private String displayName;
    private Long applicantId;
    private String officerName;

    public LoginResponse() {
    }

    public static LoginResponse fromUser(User user) {
        LoginResponse resp = new LoginResponse();
        resp.setId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setRole(user.getRole());
        resp.setDisplayName(user.getDisplayName());
        if (user.getApplicant() != null) {
            resp.setApplicantId(user.getApplicant().getId());
        }
        resp.setOfficerName(user.getOfficerName());
        return resp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }
}
