package org.example.signalingapi.service;

import org.example.signalingapi.dto.Response;
import org.springframework.http.ResponseEntity;

/**
 * @author duyenthai
 */
public interface ContactService {
    ResponseEntity<Response> addNewContact(String fromUserId, String withUser);

    ResponseEntity<Response> getAllContact(String ofUserId, int from, int size);
}
