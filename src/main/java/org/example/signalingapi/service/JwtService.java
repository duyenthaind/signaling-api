package org.example.signalingapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * @author duyenthai
 */
@Log4j2
@Component
public class JwtService {

    private static final long DAY_TO_MILLISECONDS = 24 * 60 * 60 * 1000L;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public ResponseEntity<Response> verifyAccessToken(String accessToken) {
        Key hMacKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        try {
            Jwts.parser().setSigningKey(hMacKey).parseClaimsJws(accessToken);
        } catch (InvalidKeyException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response(1, "Invalid token", null));
        } catch (ExpiredJwtException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response(2, "Access token is expired", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response(3, "Forbidden", null));
        }
        return ResponseEntity.ok(new Response(0, "Authentication ok", null));
    }

    public Claims parseAllClaims(String accessToken) {
        try {
            Key hMacKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
            return Jwts.parser().setSigningKey(hMacKey).parseClaimsJws(accessToken).getBody();
        } catch (Exception ex) {
            log.error("Parse error ", ex);
        }
        return null;
    }

    public String generateAccessToken(UserEntity user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30 * DAY_TO_MILLISECONDS))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}
