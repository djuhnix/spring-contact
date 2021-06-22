package com.cnam.contact.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("contact")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Contact extends Person {
    @Basic @Column(name="phone_number")
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
            name = "contact_address",
            joinColumns = @JoinColumn(name = "id_contact"),
            inverseJoinColumns = @JoinColumn(name = "id_address")
    )
    @ToString.Exclude
    Set<Address> addresses;

    @ManyToOne
    private User user;
}
