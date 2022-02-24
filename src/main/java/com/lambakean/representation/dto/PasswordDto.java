package com.lambakean.representation.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z]+.*$")  // todo msg
    @Size(min = 6, max = 100)  // todo msg
    private String oldPassword;

    @Pattern(regexp = "^[a-zA-Z]+.*$")  // todo msg
    @Size(min = 6, max = 100)  // todo msg
    private String newPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
