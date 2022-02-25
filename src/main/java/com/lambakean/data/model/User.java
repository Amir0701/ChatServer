package com.lambakean.data.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotNull  // todo msg
    @Column(unique = true, nullable = false)
    private String nickname;

    @NotNull  // todo msg
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull  // todo msg
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Subscription> subscriptions;

    @OneToMany(mappedBy = "user")
    private Set<RefreshTokenWrapper> refreshTokenWrappers;

    public User(Long id) {
        this.id = id;
    }

    public User() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<RefreshTokenWrapper> getRefreshTokenWrappers() {
        return refreshTokenWrappers;
    }

    public void setRefreshTokenWrappers(Set<RefreshTokenWrapper> refreshTokenWrappers) {
        this.refreshTokenWrappers = refreshTokenWrappers;
    }
}