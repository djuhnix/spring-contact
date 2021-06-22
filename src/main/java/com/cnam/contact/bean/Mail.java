package com.cnam.contact.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @ToString
public class Mail {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Basic @Column(name="id", nullable=false)
    private Long id;

    @Column(name="email")
    private String email;

    @ManyToMany(mappedBy = "mails")
    @ToString.Exclude
    Set<Contact> contacts;
}
