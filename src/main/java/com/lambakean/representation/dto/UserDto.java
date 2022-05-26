package com.lambakean.representation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    private Long id;

    @Size(min = 1, max = 50, message = "The length of the name must be between 1 and 50 characters.")
    private String name;

    @Size(min = 1, max = 50)  // todo write msg
    private String nickname;

    @Email  // todo msg
    @Pattern(regexp = "^.+@.+\\..+$")  // todo msg
    @Size(min = 5, max = 100)  // todo msg
    private String email;

    @Pattern(regexp = "^[a-zA-Z]+.*$")  // todo msg
    @Size(min = 6, max = 100)  // todo msg
    private String password;

    private String avatar;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
