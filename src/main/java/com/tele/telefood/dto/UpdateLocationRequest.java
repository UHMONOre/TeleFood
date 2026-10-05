package com.tele.telefood.dto;

public class UpdateLocationRequest {
    private String country;
    private String city;
    private String address;

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
}
