package com.cnam.contact.bean;

import com.cnam.contact.utils.PasswordMatches;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity(name = "user")
@DiscriminatorValue("user")
@Getter @Setter @ToString
@RequiredArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class User extends Person implements UserDetails {
    @Transient
    @NotNull @NotEmpty
    private String password;

    private String matchingPassword;

    @Basic @Column(name = "username", nullable = false, unique = true)
    @NotNull @NotEmpty
    private String username;

    @Basic @Column(name = "password", nullable = false)
    private String passwordHash;

    @Basic @Column(name = "salt", nullable = false)
    private String salt;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Contact> contacts;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Role> roles;

    @Builder.Default
    @Basic @Column(name = "locked")
    private Boolean locked = false;

    @Builder.Default
    @Basic @Column(name = "enabled")
    private Boolean enabled = false;

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

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role :
                roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return Collections.unmodifiableList(authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
