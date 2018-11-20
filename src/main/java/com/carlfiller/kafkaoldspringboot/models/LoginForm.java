package com.carlfiller.kafkaoldspringboot.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {

    @NotNull
    @Size(min = 5, max = 15)
    private String username;

    @NotNull
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    public LoginForm() {}

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
