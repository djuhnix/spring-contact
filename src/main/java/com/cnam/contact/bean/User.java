package com.cnam.contact.bean;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "user")
@DiscriminatorValue("user")
@Getter @Setter @ToString
@RequiredArgsConstructor
public class User extends Person {
    @Transient
    private String password;

    @Basic @Column(name = "username", nullable = false)
    private String username;

    @Basic @Column(name = "password", nullable = false)
    private String passwordHash;

    @Basic @Column(name = "salt", nullable = false)
    private String salt;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Contact> contacts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}
