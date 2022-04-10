package org.example.signalingapi.controller;

import lombok.extern.log4j.Log4j2;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.service.UserService;
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
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Response> getBasicInfo(@RequestParam String username){
        return userService.getBasicInfo(username);
    }

    @GetMapping("/users/find")
    public ResponseEntity<Response> findAndGetBasicContactInfo(@RequestParam String hint){
        return userService.findUser(hint);
    }
}
