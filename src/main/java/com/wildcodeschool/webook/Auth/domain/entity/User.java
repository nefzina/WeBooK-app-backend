package com.wildcodeschool.webook.Auth.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wildcodeschool.webook.book.domain.entity.Book;
import com.wildcodeschool.webook.book.domain.entity.Category;
import com.wildcodeschool.webook.fileUpload.domain.entity.Media;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "zipCode", length = 5)
    private String zip_code;
    @Column(name = "city", length = 10)
    private String city;
    @Column(name = "isEnabled", nullable = false)
    private Boolean isEnabled;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnore
    private Role role;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "category_preferences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> preferences;
    @JsonManagedReference(value="user-books")
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private Media profilePicture;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PasswordToken> passwordTokens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Category> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Category> preferences) {
        this.preferences = preferences;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Media getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Media profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<PasswordToken> getPasswordTokens() {
        return passwordTokens;
    }

    public void setPasswordTokens(List<PasswordToken> passwordTokens) {
        this.passwordTokens = passwordTokens;
    }
}
