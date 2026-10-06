package com.tele.telefood.entity;

import com.tele.telefood.dto.RegisterRequestCustomer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends User {
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Order> orderHistory = new ArrayList<>();

    protected Customer() {
        super();
    }

    public Customer(RegisterRequestCustomer request) {
        super(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), request.getCountry(), request.getCity(), request.getAddress(), request.getPhoneNumber());
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }
}
