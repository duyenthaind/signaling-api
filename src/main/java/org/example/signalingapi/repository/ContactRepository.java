package org.example.signalingapi.repository;

import org.example.signalingapi.entity.ContactEntity;
import org.example.signalingapi.repository.custom.CustomContactRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author duyenthai
 */
public interface ContactRepository extends JpaRepository<ContactEntity, Long>, CustomContactRepository {

    Optional<ContactEntity> findByCreatorAndAndContactId(String creator, String contactId);

}
