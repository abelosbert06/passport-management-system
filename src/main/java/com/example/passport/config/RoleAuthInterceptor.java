package com.example.passport.config;

import com.example.passport.controller.AuthController;
import com.example.passport.dto.LoginResponse;
import com.example.passport.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 1. Officer endpoints: /api/officer/**
        if (uri.startsWith("/api/officer")) {
            HttpSession session = request.getSession(false);
            LoginResponse user = session != null ? (LoginResponse) session.getAttribute(AuthController.SESSION_USER) : null;

            if (user == null) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication required. Please log in as an officer.");
                return false;
            }
            if (user.getRole() != Role.OFFICER) {
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Verification Officer role required.");
                return false;
            }
            return true;
        }

        // 2. Submitting passport application: POST /api/applications
        if (uri.equals("/api/applications") && HttpMethod.POST.matches(method)) {
            HttpSession session = request.getSession(false);
            LoginResponse user = session != null ? (LoginResponse) session.getAttribute(AuthController.SESSION_USER) : null;

            if (user == null) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Please log in to submit a passport application.");
                return false;
            }
            if (user.getRole() != Role.APPLICANT) {
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Only applicants can submit passport applications.");
                return false;
            }
            return true;
        }

        // 3. Applicant personal applications: GET /api/applications/my
        if (uri.startsWith("/api/applications/my")) {
            HttpSession session = request.getSession(false);
            LoginResponse user = session != null ? (LoginResponse) session.getAttribute(AuthController.SESSION_USER) : null;

            if (user == null) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Please log in to view your applications.");
                return false;
            }
            if (user.getRole() != Role.APPLICANT) {
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied.");
                return false;
            }
            return true;
        }

        return true;
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"status\":%d,\"error\":\"%s\",\"message\":\"%s\"}",
                status, status == 401 ? "Unauthorized" : "Forbidden", message));
    }
}
