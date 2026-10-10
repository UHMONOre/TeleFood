package com.tele.telefood.dto;

public class CreateItemRequest {
    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer stock;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
