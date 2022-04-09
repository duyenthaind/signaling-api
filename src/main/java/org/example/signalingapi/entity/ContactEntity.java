package org.example.signalingapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author duyenthai
 */

@Entity
@Table(name = "dh_contact")
@Setter
@Getter
@ToString
public class ContactEntity extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "creator", nullable = false, length = 50)
    private String creator;
    @Column(name = "contact_id", nullable = false, length = 50)
    private String contactId;

}
