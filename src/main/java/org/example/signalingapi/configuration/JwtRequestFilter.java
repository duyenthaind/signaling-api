package org.example.signalingapi.configuration;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.entity.UserEntity;
import org.example.signalingapi.service.AuthenticationService;
import org.example.signalingapi.service.JwtService;
import org.example.signalingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author duyenthai
 */
@Log4j2
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private AuthenticationService authenticationService;
    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ResponseEntity<Response> responseEntity = authenticationService.authenticateRequest(request);
        Response res = responseEntity.getBody();
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            response.sendError(responseEntity.getStatusCodeValue(), res != null ? res.getMsg() : "Forbidden");
            return;
        }
        if (res == null) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "User not found");
            return;
        } else {
            final String accessToken = request.getHeader("Authorization");
            Claims claims = jwtService.parseAllClaims(accessToken);
            String userId = claims.get("userId", String.class);
            Optional<UserEntity> userEntity = userService.findByUserId(userId);
            if (userEntity.isEmpty()) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "User not found");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
