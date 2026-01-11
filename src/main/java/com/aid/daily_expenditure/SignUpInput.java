package com.aid.daily_expenditure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class SignUpInput {

    @NotBlank(message = "UserID should not be blank.\n")
    @Size(max = 30,
            message = "UserID should have at most 30 characters.\n")
    @Pattern(regexp = "^[A-Za-z0-9-_]+$",
            message = "UserID should contains only alphabets, digits, hyphens(-) and underscores(_).\n")
    private String userId;

    @NotBlank(message = "Email Address should not be blank.\n")
    @Email(message = "Email Address not valid.\n")
    private String email;

    @NotBlank(message = "Password should not be blank.\n")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9]).+$",
            message = "Password should contains at least 1 lowercase letter and 1 digit.\n")
    @Size(min = 8, max = 32,
            message = "Password length should be between 8 to 32.\n")
    @Pattern(regexp = "^[A-Za-z0-9@$!%*?&#+_-]+$",
            message = "Password should contains only alphabets, digits, and special characters @$!%*?&#+_- .\n")
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
