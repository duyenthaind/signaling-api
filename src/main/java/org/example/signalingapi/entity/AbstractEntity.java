package org.example.signalingapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author duyenthai
 */
@MappedSuperclass
@Setter
@Getter
@ToString
public abstract class AbstractEntity {
    @Column(name = "createdAt")
    private long createdAt;
    @Column(name = "updatedAt")
    private long updatedAt;

}
