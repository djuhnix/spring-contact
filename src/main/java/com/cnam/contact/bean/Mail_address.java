package com.cnam.contact.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class Mail_address extends Contact {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Basic @Column(name="id", nullable=false)
    private Long id;

    @Column(name="mail")
    private String mail;
}
