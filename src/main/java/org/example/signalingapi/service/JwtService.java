package org.example.signalingapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author duyenthai
 */
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;
}
