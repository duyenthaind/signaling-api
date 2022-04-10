package org.example.signalingapi.service.impl;

import org.example.signalingapi.dto.Response;
import org.example.signalingapi.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author duyenthai
 */
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    @Disabled
    void getBasicInfo() {
        ResponseEntity<Response> info = userService.getBasicInfo("darling");
        Response res = info.getBody();
        assertNotNull(res);
    }
}