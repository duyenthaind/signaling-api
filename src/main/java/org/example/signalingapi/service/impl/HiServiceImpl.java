package org.example.signalingapi.service.impl;

import lombok.extern.log4j.Log4j2;
import org.example.signalingapi.service.HiService;
import org.springframework.stereotype.Service;

/**
 * @author duyenthai
 */
@Log4j2
@Service
public class HiServiceImpl implements HiService {
    @Override
    public String sayHi() {
        log.info("===========Say hi===========");
        return "hi";
    }
}
