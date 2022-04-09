package org.example.signalingapi.controller;

import lombok.extern.log4j.Log4j2;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.request.LoginRequest;
import org.example.signalingapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author duyenthai
 */
@Log4j2
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class LoginController {

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.performLogin(loginRequest);
    }
}
