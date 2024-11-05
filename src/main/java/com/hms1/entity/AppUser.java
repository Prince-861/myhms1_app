package com.hms1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//Here in the JPAEntity we have created AppUser class and not the User class because User class is very common in Spring Security and it is built-in class as well. So, we should avoid creating this as class name.
@Getter
@Setter
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @Column(name="role", nullable = false, length = 20)
    private String role;

}