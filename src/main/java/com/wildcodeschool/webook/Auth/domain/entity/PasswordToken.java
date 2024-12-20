package com.wildcodeschool.webook.Auth.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "dateOfExpiration", nullable = false)
    private LocalDateTime dateOfExpiration;
    @Column(name = "isChanged", nullable = false)
    private Boolean isChanged;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDateOfExpiration() {
        return dateOfExpiration;
    }

    public void setDateOfExpiration(LocalDateTime dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
    }

    public Boolean getChanged() {
        return isChanged;
    }

    public void setChanged(Boolean changed) {
        isChanged = changed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
