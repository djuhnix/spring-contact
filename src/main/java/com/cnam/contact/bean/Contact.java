package com.cnam.contact.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    List<Address> addresses;

    @OneToMany(mappedBy = "contact")
    @ToString.Exclude
    List<Mail> mails;

    @ManyToOne
    private User user;

    public Contact(Mail mail, Address address) {
        mail.setEmail("");
        address.setNumPath(0);
        address.setAdditional("");
        address.setPathLabel("");
        address.setCity("");
        address.setPostalCode("");
        this.addresses = new ArrayList<Address>();
        this.addresses.add(address);
        this.mails = new ArrayList<Mail>();
        this.mails.add(mail);
    }
}
