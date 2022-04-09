package org.example.signalingapi.repository;

import org.example.signalingapi.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author duyenthai
 */
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
}
