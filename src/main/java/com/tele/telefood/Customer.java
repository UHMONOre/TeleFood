package com.tele.telefood;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends User{
    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Order> orderHistory = new ArrayList<>();

    protected Customer() {
        super();
    }

    public Customer(String email, String password, String firstName, String lastName, String country, String city, String address) {
        super(email, password, firstName, lastName);
        this.country = country;
        this.city = city;
        this.address = address;
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


}
