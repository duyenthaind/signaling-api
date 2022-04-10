package org.example.signalingapi.service;

import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.request.RegisterRequest;
import org.example.signalingapi.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * @author duyenthai
 */
public interface UserService {
    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByUserName(String username);

    ResponseEntity<Response> createNewUser(RegisterRequest registerRequest);

    ResponseEntity<Response> getBasicInfo(String username);

    ResponseEntity<Response> findUser(String hint);
}
