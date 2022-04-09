package org.example.signalingapi.controller;

import lombok.extern.log4j.Log4j2;
import org.example.signalingapi.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duyenthai
 */
@Log4j2
@RestController
@RequestMapping("/hi")
public class HiController {

    private HiService hiService;

    @Autowired
    public void setHiService(HiService hiService) {
        this.hiService = hiService;
    }

    @GetMapping
    public ResponseEntity<String> getHi(){
        String hi = hiService.sayHi();
        return ResponseEntity.ok(hi);
    }
}
