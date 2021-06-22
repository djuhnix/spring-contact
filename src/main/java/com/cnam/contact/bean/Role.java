package com.cnam.contact.bean;

import lombok.*;

import javax.persistence.*;

@Entity(name = "role")
@Getter @Setter @ToString
@RequiredArgsConstructor
public class Role {
    public enum ROLE {
        ADMIN, USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic @NonNull @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private User user;

}
