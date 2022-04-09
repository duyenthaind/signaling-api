package org.example.signalingapi.repository;

import org.example.signalingapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author duyenthai
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserName(String username);

}
