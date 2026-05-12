package com.example.blog.dto;

import jakarta.validation.constraints.*;

public class RegisterDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "Пароль мінімум 6 символів")
    private String password;

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 6, message = "Пароль мінімум 6 символів") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6, message = "Пароль мінімум 6 символів") String password) {
        this.password = password;
    }
}