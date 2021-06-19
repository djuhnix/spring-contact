package com.cnam.contact.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@DiscriminatorValue("contact")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Contact extends Person {
    @Basic @Column(name="phone_number")
    private String phoneNumber;

    /*
    @Basic @Column(name="mail")
    private String mail;
    */

    @ManyToOne
    private User user;
}
