package com.wildcodeschool.webook.book.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    private String type;

    @JsonIgnore
    @ManyToMany(mappedBy = "preferences", cascade = CascadeType.REMOVE)
    private List<User> users;

    @JsonIgnoreProperties("book-category")
    //@JsonManagedReference(value="book-category")
    @OneToMany(mappedBy = "bookCategory", cascade = CascadeType.REFRESH)
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
