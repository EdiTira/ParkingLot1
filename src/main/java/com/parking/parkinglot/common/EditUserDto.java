package com.parking.parkinglot.common;

public class EditUserDto {
    private Long id;
    private String username;
    private String password;
    private String email;

    public EditUserDto(Long id, String username, String email, String password) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

