package com.tele.telefood.dto;

public class CreateItemRequest {
    private String name;
    private String description;
    private Double price;
    private Integer vendorId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getVendorId() {
        return vendorId;
    }
}
