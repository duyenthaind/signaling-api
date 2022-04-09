package org.example.signalingapi.service.impl;

import lombok.extern.log4j.Log4j2;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.request.RegisterRequest;
import org.example.signalingapi.entity.UserEntity;
import org.example.signalingapi.repository.UserRepository;
import org.example.signalingapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author duyenthai
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public ResponseEntity<Response> createNewUser(RegisterRequest registerRequest) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByUserName(registerRequest.getUsername());
            if (userEntity.isPresent()) {
                return ResponseEntity.badRequest().body(
                        new Response(2, String.format("UserName %s has been used ", registerRequest.getUsername()), null));
            }
            UserEntity newUser = new UserEntity();
            BeanUtils.copyProperties(registerRequest, newUser);
            userRepository.save(newUser);
            return ResponseEntity.ok(new Response(0, "Ok", null));
        } catch (Exception ex) {
            log.error("Create new user error ", ex);
        }
        return ResponseEntity.badRequest().body(new Response(1, "Error ", null));
    }

    @Deprecated
    UserEntity fromRegisterRequest(RegisterRequest registerRequest) {
        UserEntity newUser = new UserEntity();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUserName(registerRequest.getUsername());
        newUser.setName(registerRequest.getName());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setAvatarUrl(registerRequest.getAvatarUrl());
        newUser.setCreatedAt(System.currentTimeMillis());
        newUser.setUpdatedAt(System.currentTimeMillis());
        return newUser;
    }
}
