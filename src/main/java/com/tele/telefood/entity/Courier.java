package com.tele.telefood.entity;

import com.tele.telefood.dto.RegisterRequestCourier;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courier")
public class Courier extends User{
    @Column(nullable = false, unique = true)
    private Long tin;

    @Column(nullable = false)
    private Boolean available = false;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public Courier() {}

    public Courier(String email, String password, String firstName, String lastName, String country, String city, String address, String phoneNumber) {
        super(email, password, firstName, lastName, country, city, address, phoneNumber);
    }

    public Courier(String email, String password, String firstName, String lastName, String country, String city, String address, String phoneNumber, Long tin) {
        super(email, password, firstName, lastName, country, city, address, phoneNumber);
        this.tin = tin;
    }

    public Courier(RegisterRequestCourier request) {
        super(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), request.getCountry(), request.getCity(), request.getAddress(), request.getPhoneNumber());
        this.tin = request.getTin();
    }

    public Long getTin() {
        return tin;
    }

    public void setTin(Long tin) {
        this.tin = tin;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
