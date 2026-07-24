package com.cheq.contactlist.models.userrequestmodel;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // --- SETTER ---
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // --- GETTER ---
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }



}