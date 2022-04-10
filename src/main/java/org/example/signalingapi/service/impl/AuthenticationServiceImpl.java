package org.example.signalingapi.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.request.LoginRequest;
import org.example.signalingapi.entity.UserEntity;
import org.example.signalingapi.service.AuthenticationService;
import org.example.signalingapi.service.JwtService;
import org.example.signalingapi.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author duyenthai
 */
@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private JwtService jwtService;
    private UserService userService;

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Response> authenticateRequest(HttpServletRequest httpRequest) {
        final String accessToken = httpRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(accessToken)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Response(-1, "Request header must have access token", null));
        }
        return jwtService.verifyAccessToken(accessToken);
    }

    @Override
    public ResponseEntity<Response> performLogin(LoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        if (StringUtils.isEmpty(userName)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response(-1, "Username must be not null", null));
        }
        try {
            Optional<UserEntity> user = userService.findByUserName(userName);
            if (user.isEmpty() || !user.get().getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Response(2, "Username or password is incorrect", null));
            } else {
                String accessToken = jwtService.generateAccessToken(user.get());
                JSONObject data = new JSONObject();
                data.put("accessToken", accessToken);
                Response res = new Response(0, "Success", data.toString());
                return ResponseEntity.ok(res);
            }
        } catch (Exception ex) {
            log.error("Exception when perform login", ex);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Response(1, "Error", null));
    }


}
