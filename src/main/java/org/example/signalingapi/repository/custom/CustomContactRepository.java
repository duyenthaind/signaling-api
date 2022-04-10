package org.example.signalingapi.repository.custom;

import java.util.List;

/**
 * @author duyenthai
 */
public interface CustomContactRepository {
    List<String> getAllContactIdOfUser(String ofUser, int from, int maxResult);
}
