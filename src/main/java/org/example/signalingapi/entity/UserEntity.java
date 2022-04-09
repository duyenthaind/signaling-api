package org.example.signalingapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author duyenthai
 */
@Entity
@Table(name = "dh_user")
@Setter
@Getter
@ToString
public class UserEntity extends AbstractEntity implements Serializable {
    @Id
    @Column(name = "id", length = 50)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "avatar_url", columnDefinition = "text")
    private String avatarUrl;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;

}
