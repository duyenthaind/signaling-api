package org.example.signalingapi.repository;

import org.example.signalingapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author duyenthai
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserName(String username);

    Optional<UserEntity> findByName(String name);

    @Query(value = "from UserEntity where id in :userIds")
    List<UserEntity> findByListUserId(@Param("userIds") List<String> userIds);
}
