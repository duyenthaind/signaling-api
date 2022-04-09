package org.example.signalingapi.repository;

import org.example.signalingapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author duyenthai
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
