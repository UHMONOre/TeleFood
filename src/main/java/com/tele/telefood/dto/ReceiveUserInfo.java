package com.tele.telefood.dto;

import com.tele.telefood.entity.Courier;
import com.tele.telefood.entity.Customer;
import com.tele.telefood.entity.Vendor;

public class ReceiveUserInfo {
    private String email;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String address;
    private String phoneNumber;
    private String name;
    private String description;
    private Long tin;

    public ReceiveUserInfo(Customer customer){
        this.email = customer.getEmail();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.country = customer.getCountry();
        this.city = customer.getCity();
        this.address = customer.getAddress();
        this.phoneNumber = customer.getPhoneNumber();
    }

    public ReceiveUserInfo(Courier courier){
        this.email = courier.getEmail();
        this.firstName = courier.getFirstName();
        this.lastName = courier.getLastName();
        this.country = courier.getCountry();
        this.city = courier.getCity();
        this.address = courier.getAddress();
        this.phoneNumber = courier.getPhoneNumber();
        this.tin = courier.getTin();
    }

    public ReceiveUserInfo(Vendor vendor){
        this.email = vendor.getEmail();
        this.firstName = vendor.getFirstName();
        this.lastName = vendor.getLastName();
        this.country = vendor.getCountry();
        this.city = vendor.getCity();
        this.address = vendor.getAddress();
        this.phoneNumber = vendor.getPhoneNumber();
        this.name = vendor.getName();
        this.description = vendor.getDescription();
        this.tin = vendor.getTin();
    }

    public String getEmail() {
        return email;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getTin() {
        return tin;
    }
}
