package org.example.signalingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author duyenthai
 */
@Data
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private int res;
    private String msg;
    private String data;

}
