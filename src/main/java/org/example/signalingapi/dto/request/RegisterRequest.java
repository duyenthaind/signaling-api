package org.example.signalingapi.dto.request;

import lombok.Data;

/**
 * @author duyenthai
 */
@Data
public class RegisterRequest {
    private String name;
    private String avatarUrl;
    private String username;
    private String password;
}
