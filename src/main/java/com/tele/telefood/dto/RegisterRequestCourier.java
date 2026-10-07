package com.tele.telefood.dto;

public class RegisterRequestCourier {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String address;
    private String phoneNumber;
    private Long tin;

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

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getTin() {
        return tin;
    }
}
