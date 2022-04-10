package org.example.signalingapi.controller;

import lombok.extern.log4j.Log4j2;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.service.ContactService;
import org.example.signalingapi.service.JwtService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author duyenthai
 */
@Log4j2
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ContactController {
    private ContactService contactService;
    private JwtService jwtService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/contacts")
    public ResponseEntity<Response> addNewContact(@RequestBody String username, HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String userId = jwtService.parseAllClaims(accessToken).get("userId", String.class);
        try {
            JSONObject json = new JSONObject(username);
            username = json.optString("username");
        } catch (Exception ex) {
            log.error("Error ", ex);
        }
        return contactService.addNewContact(userId, username);
    }

    @GetMapping("/contacts")
    public ResponseEntity<Response> getAllContact(@RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "5") int maxResult, HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String userId = jwtService.parseAllClaims(accessToken).get("userId", String.class);
        return contactService.getAllContact(userId, from, maxResult);
    }
}
