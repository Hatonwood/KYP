package com.localhost.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class AuthenticationBean {

    @NotBlank
    @Size(max = 30)
    private String username;

    private String password;

    public AuthenticationBean(@NotBlank @Size(max = 30) String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationBean() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
