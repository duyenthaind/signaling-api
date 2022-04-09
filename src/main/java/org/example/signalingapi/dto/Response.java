package org.example.signalingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.configurationprocessor.json.JSONObject;

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
    private JSONObject data;

    public void setField(String key, Object value) {
        if (data == null) {
            data = new JSONObject();
        }
        try {
            data.put(key, value);
        } catch (Exception ex) {
            log.error("Put map to json error ", ex);
        }
    }

    public void removeField(String key) {
        if (data != null) {
            data.remove(key);
        }
    }
}
