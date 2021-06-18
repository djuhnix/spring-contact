package com.cnam.contact.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "user")
@DiscriminatorValue("user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User extends Person {
    @Transient
    private String password;

    @Basic @Column(name = "username", nullable = false)
    private String username;

    @Basic @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Basic @Column(name = "salt", nullable = false)
    private String salt;
}
