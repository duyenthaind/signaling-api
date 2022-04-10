package org.example.signalingapi.dto.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author duyenthai
 */
@Data
@ToString
public class UserInfoResponse {
    private String name;
    private String userName;
    private String avatarUrl;
    private String userId;
}
