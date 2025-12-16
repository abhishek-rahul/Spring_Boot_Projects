package com.abhicom.userservice.dto;

import com.abhicom.userservice.validation.AllowedEmailDomain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {

    @NotBlank(message = "firstName is required")
    @Size(min = 2, max = 50, message = "firstName must be 2 to 50 characters")
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Size(min = 2, max = 50, message = "lastName must be 2 to 50 characters")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    @AllowedEmailDomain(allowed = {"gmail.com", "outlook.com"}, message = "only gmail.com and outlook.com emails are allowed")
    private String email;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
