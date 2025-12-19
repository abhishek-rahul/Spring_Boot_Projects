package com.abhicom.userservice.dto;

import java.util.List;

public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressDto> addresses;

    // getters & setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<AddressDto> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }
}