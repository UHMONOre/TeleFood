package com.tele.telefood.entity;

import com.tele.telefood.dto.RegisterRequestVendor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor")
public class Vendor extends User {
    @Column(nullable = false, unique = true)
    private Long tin;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public Vendor() {}

    public Vendor(String email, String password, String firstName, String lastName, String country, String city, String address, String phoneNumber) {
        super(email, password, firstName, lastName, country, city, address, phoneNumber);
    }

    public Vendor(String email, String password, String firstName, String lastName, String country, String city, String address, String phoneNumber, Long tin, String name, String description) {
        super(email, password, firstName, lastName, country, city, address, phoneNumber);
        this.tin = tin;
        this.name = name;
        this.description = description;
    }

    public Vendor(RegisterRequestVendor request) {
        super(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), request.getCountry(), request.getCity(), request.getAddress(), request.getPhoneNumber());
        this.tin = request.getTin();
        this.name = request.getName();
    }

    public Long getTin() {
        return tin;
    }

    public void setTin(Long tin) {
        this.tin = tin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
