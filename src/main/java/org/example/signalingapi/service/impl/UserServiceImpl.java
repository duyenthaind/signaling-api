package org.example.signalingapi.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.request.RegisterRequest;
import org.example.signalingapi.dto.response.ContactInfoResponse;
import org.example.signalingapi.dto.response.UserInfoResponse;
import org.example.signalingapi.entity.UserEntity;
import org.example.signalingapi.repository.UserRepository;
import org.example.signalingapi.service.UserService;
import org.json.JSONObject;
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
            Optional<UserEntity> userEntity = userRepository.findByUserName(registerRequest.getUserName());
            if (userEntity.isPresent()) {
                return ResponseEntity.badRequest().body(
                        new Response(2, String.format("UserName %s has been used ", registerRequest.getUserName()), null));
            }
            UserEntity newUser = new UserEntity();
            BeanUtils.copyProperties(registerRequest, newUser);
            newUser.setId(UUID.randomUUID().toString());
            userRepository.save(newUser);
            return ResponseEntity.ok(new Response(0, "Ok", null));
        } catch (Exception ex) {
            log.error("Create new user error ", ex);
        }
        return ResponseEntity.badRequest().body(new Response(1, "Error ", null));
    }

    @Override
    public ResponseEntity<Response> getBasicInfo(String username) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByUserName(username);
            if (userEntity.isEmpty()) {
                return ResponseEntity.ok(new Response(2, "Not found user info", null));
            }
            UserInfoResponse userInfoResponse = new UserInfoResponse();
            BeanUtils.copyProperties(userEntity.get(), userInfoResponse);
            userInfoResponse.setUserId(userEntity.get().getId());
            JSONObject data = new JSONObject();
            data.put("userInfo", userInfoResponse);
            Response response = new Response(0, "Success", data.toString());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error(String.format("Find basic info from username %s error ", username), ex);
        }
        return ResponseEntity.badRequest().body(new Response(1, "Error", null));
    }

    @Override
    public ResponseEntity<Response> findUser(String hint) {
        try {
            if (StringUtils.isEmpty(hint)) {
                log.error("Empty hint");
            } else {
                Optional<UserEntity> o;
                if (hint.startsWith("@")) {
                    o = userRepository.findByUserName(hint.substring(1));
                } else {
                    o = userRepository.findByName(hint);
                }
                if (o.isEmpty()) {
                    return ResponseEntity.ok(new Response(2, "Not found user info", null));
                }
                ContactInfoResponse contactInfoResponse = new ContactInfoResponse();
                BeanUtils.copyProperties(o.get(), contactInfoResponse);
                JSONObject data = new JSONObject();
                data.put("contactInfo", contactInfoResponse);
                Response response = new Response(0, "Success", data.toString());
                return ResponseEntity.ok(response);
            }
        } catch (Exception ex) {
            log.error("Error find ", ex);
        }
        return ResponseEntity.badRequest().body(new Response(1, "Error", null));
    }

    @Deprecated
    UserEntity fromRegisterRequest(RegisterRequest registerRequest) {
        UserEntity newUser = new UserEntity();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUserName(registerRequest.getUserName());
        newUser.setName(registerRequest.getName());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setAvatarUrl(registerRequest.getAvatarUrl());
        newUser.setCreatedAt(System.currentTimeMillis());
        newUser.setUpdatedAt(System.currentTimeMillis());
        return newUser;
    }
}
