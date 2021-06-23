package com.cnam.contact.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull @NotEmpty
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "contact_address",
            joinColumns = @JoinColumn(name = "id_contact"),
            inverseJoinColumns = @JoinColumn(name = "id_address")
    )
    @ToString.Exclude
    List<Address> addresses;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    List<Mail> mails;

    @ManyToOne
    private User user;

    public Contact(Mail mail, Address address) {
        mail.setEmail("");
        this.mails = new ArrayList<Mail>();
        this.mails.add(mail);
        address.setNumPath(0);
        address.setAdditional("");
        address.setPathLabel("");
        address.setCity("");
        address.setPostalCode("");
        this.addresses = new ArrayList<Address>();
        this.addresses.add(address);
    }
}
