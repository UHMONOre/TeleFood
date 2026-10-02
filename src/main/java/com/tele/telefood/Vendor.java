package com.tele.telefood;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor")
public class Vendor extends User{
    @Column(nullable = false, unique = true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Long tin;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private List<Item> items = new ArrayList<>();

    public Vendor(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }

    public Vendor(String email, String password, String firstName, String lastName, Long tin, String name, String description, List<Item> items) {
        super(email, password, firstName, lastName);
        this.tin = tin;
        this.name = name;
        this.description = description;
        this.items = items;
    }

    public Vendor(Long tin, String name, String description, List<Item> items) {
        this.tin = tin;
        this.name = name;
        this.description = description;
        this.items = items;
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
}
