package com.aid.daily_expenditure.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class SignUpInput {

    @NotBlank(message = "UserID should not be blank.")
    @Size(max = 30,
            message = "UserID should have at most 30 characters.")
    @Pattern(regexp = "^[A-Za-z0-9-_]+$",
            message = "UserID should contains only alphabets, digits, hyphens(-) and underscores(_).")
    private String userId;

    @NotBlank(message = "Email Address should not be blank.")
    @Email(message = "Email Address not valid.")
    private String email;

    @NotBlank(message = "Password should not be blank.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9]).+$",
            message = "Password should contains at least 1 lowercase letter and 1 digit.")
    @Size(min = 8, max = 32,
            message = "Password length should be between 8 to 32.")
    @Pattern(regexp = "^[A-Za-z0-9@$!%*?&#+_-]+$",
            message = "Password should contains only alphabets, digits, and special characters @$!%*?&#+_- .")
    private String password;

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
}
