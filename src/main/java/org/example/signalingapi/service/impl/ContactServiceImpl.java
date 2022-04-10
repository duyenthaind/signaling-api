package org.example.signalingapi.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.signalingapi.dto.Response;
import org.example.signalingapi.dto.response.UserInfoResponse;
import org.example.signalingapi.entity.ContactEntity;
import org.example.signalingapi.entity.UserEntity;
import org.example.signalingapi.repository.ContactRepository;
import org.example.signalingapi.repository.UserRepository;
import org.example.signalingapi.service.ContactService;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author duyenthai
 */
@Log4j2
@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;
    private UserRepository userRepository;

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Response> addNewContact(String fromUserId, String withUser) {
        try {
            Optional<UserEntity> user = userRepository.findByUserName(withUser);
            if (user.isEmpty() || StringUtils.isEmpty(withUser)) {
                return ResponseEntity.badRequest()
                        .body(new Response(1, "Target user not found", null));
            }
            Optional<UserEntity> thisUser = userRepository.findById(fromUserId);
            Objects.requireNonNull(thisUser.isEmpty() ? null : "no");
            Optional<ContactEntity> contact = contactRepository.findByCreatorAndAndContactId(fromUserId, user.get().getId());
            if (contact.isPresent()) {
                return ResponseEntity.badRequest()
                        .body(new Response(2, "Target user is already contact", null));
            }
            ContactEntity contactEntity = genNewContact(fromUserId, user.get().getId());
            ContactEntity e = contactRepository.save(contactEntity);
            log.info("Saved new contact with id " + e.getId());
            return ResponseEntity.ok(new Response(0, "Ok", null));
        } catch (Exception ex) {
            log.error("Add new contact error ", ex);
        }
        return ResponseEntity.badRequest().body(new Response(1, "Error ", null));
    }

    @Override
    public ResponseEntity<Response> getAllContact(String ofUserId, int from, int size) {
        try {
            List<String> allContactIds = contactRepository.getAllContactIdOfUser(ofUserId, from, size);
            List<UserEntity> listUsers = userRepository.findByListUserId(allContactIds);
            List<UserInfoResponse> list = listUsers.stream().map(val -> {
                UserInfoResponse userInfoResponse = new UserInfoResponse();
                BeanUtils.copyProperties(val, userInfoResponse);
                userInfoResponse.setUserId(val.getId());
                return userInfoResponse;
            }).collect(Collectors.toList());
            JSONObject data = new JSONObject();
            data.put("data", list);
            return ResponseEntity.ok(new Response(0, "ok", data.toString()));
        } catch (Exception ex) {
            log.error("Error get all contact ", ex);
        }
        return ResponseEntity.badRequest().body(new Response(1, "Error ", null));
    }

    ContactEntity genNewContact(String ofUser, String targetUser) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setCreator(ofUser);
        contactEntity.setContactId(targetUser);
        contactEntity.setCreatedAt(System.currentTimeMillis());
        contactEntity.setUpdatedAt(System.currentTimeMillis());
        return contactEntity;
    }
}
