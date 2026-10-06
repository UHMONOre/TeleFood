package com.tele.telefood.dto;

public class RegisterRequestVendor {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long tin;
    private String country;
    private String city;
    private String address;
    private String name;
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getTin() {
        return tin;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
