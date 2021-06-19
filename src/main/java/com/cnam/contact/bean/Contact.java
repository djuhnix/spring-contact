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
    @Basic @Column
    private String phone;
    @ManyToOne
    private User user;
}
