package org.example.signalingapi.service;

import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author duyenthai
 */
public interface AuthenticationService {
    ResponseEntity<Response> authenticateRequest(HttpServletRequest httpRequest);

    ResponseEntity<Response> performLogin(LoginRequest loginRequest);
}
