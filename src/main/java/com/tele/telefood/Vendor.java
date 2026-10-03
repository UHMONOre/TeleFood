package com.tele.telefood;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor")
public class Vendor extends User{
    @Column(nullable = false, unique = true)
    private Long tin;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public Vendor() {}

    public Vendor(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }

    public Vendor(String email, String password, String firstName, String lastName, Long tin, String country, String city, String address, String name, String description, List<Item> items, List<Order> orders) {
        super(email, password, firstName, lastName);
        this.tin = tin;
        this.country = country;
        this.city = city;
        this.address = address;
        this.name = name;
        this.description = description;
        this.items = items;
        this.orders = orders;
    }

    public Vendor(RegisterRequestVendor request){
        super(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());

        this.tin = request.getTin();
        this.country = request.getCountry();
        this.city = request.getCity();
        this.address = request.getAddress();
        this.name = request.getName();
    }

    public Long getTin() {
        return tin;
    }

    public void setTin(Long tin) {
        this.tin = tin;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
