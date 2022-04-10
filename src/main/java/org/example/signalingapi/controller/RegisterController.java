package org.example.signalingapi.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.request.RegisterRequest;
import org.example.signalingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duyenthai
 */
@Log4j2
@RestController
@RequestMapping("/api/v1")
public class RegisterController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerNewUser(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest == null) {
            return ResponseEntity.badRequest().body(new Response(-1, "Body must not be not null", null));
        }
        if (StringUtils.isEmpty(registerRequest.getName()) || StringUtils.isEmpty(registerRequest.getPassword()) || StringUtils.isEmpty(registerRequest.getUserName())) {
            log.error("Invalid body request " + registerRequest);
            return ResponseEntity.badRequest().body(new Response(-1, "Invalid body request", null));
        }
        return userService.createNewUser(registerRequest);
    }
}
