package com.tele.telefood.entity;

import com.tele.telefood.dto.RegisterRequestCustomer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends User {
    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Order> orderHistory = new ArrayList<>();

    protected Customer() {
        super();
    }

    public Customer(String email, String password, String firstName, String lastName, String country, String city, String address, String phoneNumber) {
        super(email, password, firstName, lastName);
        this.country = country;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer(RegisterRequestCustomer request){
        super(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());

        this.country = request.getCountry();
        this.city = request.getCity();
        this.address = request.getAddress();
        this.phoneNumber = request.getPhoneNumber();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }
}
