package com.cnam.contact.bean;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity(name="Address")
@Getter @Setter @ToString
public class Address {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic @Column(name="id", nullable=false)
    private Long id;

    @Basic @Column(name="num_path")
    private int numPath;

    @Basic @Column(name="additional")
    private String additional;

    @Basic @Column(name="path_label")
    private String pathLabel;

    @Basic @Column(name="city")
    private String city;

    @Basic @Column(name="postal_code")
    private String postalCode;

    @ManyToMany(mappedBy = "addresses")
    @ToString.Exclude
    List<Contact> contacts;
}
