package org.example.signalingapi.dto.request;

import lombok.Data;

/**
 * @author duyenthai
 */
@Data
public class LoginRequest {
    private String userName;
    private String password;
}
