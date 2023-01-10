package com.goodmortician.notes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@ToString
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "\"user\"")

public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "login")
    private String login;
    @Column (name = "active")
    private Boolean active;
    @Column (name = "password")
    private String password;
    @Column (name = "first_name")
    private String firstName;
    @Column (name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable (name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;


}

