package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "registrationNumber"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable /*DateAudit*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@NotBlank
    @Size(min = 4, max = 40)
    private String name;

    //@NotBlank
    @Size(min = 3, max = 15)
    private String registrationNumber;

    @NaturalId
    //@NotBlank
    @Size(max = 40)
    @Email
    private String email;

    //@NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @Transient
    //@NotBlank
    private String registrationNumberOrEmail;

    @OneToOne
    //@JsonManagedReference
    private Agency agency;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /*@OneToOne(mappedBy = "user")
    private Teller teller;

    @JsonIgnore
    public Teller getTeller() {
        return teller;
    }

    public void setTeller(Teller teller) {
        this.teller = teller;
    }*/

    public User() {

    }

    public User(String name, String registrationNumber, String email, String password) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getRegistrationNumberOrEmail() {
        return registrationNumberOrEmail;
    }

    public void setRegistrationNumberOrEmail(String registrationNumberOrEmail) {
        this.registrationNumberOrEmail = registrationNumberOrEmail;
    }

    @Transient
    private Instant joinedAt;

    public void UserProfile(long id, String registrationNumber, String name, Instant joinedAt) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.joinedAt = joinedAt;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Transient
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void UserSummary(long id, String registrationNumber, String name, String role) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.role = role;
    }
}
