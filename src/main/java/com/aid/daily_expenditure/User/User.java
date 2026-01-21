package com.aid.daily_expenditure.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private String userId;
    private String email;
    private String password;
    //private boolean active;
    private String roles;


    public User() {}

    public User(SignUpInput signUpInput) {
        this.userId = signUpInput.getUserId();
        this.email = signUpInput.getEmail();
        this.password = signUpInput.getPassword();
        this.roles = "ROLE_USER";
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
/*
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
*/
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
