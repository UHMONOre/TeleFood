package com.tele.telefood.dto;

public class UpdateItemRequest {
    private Integer itemId;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Double discount;
    private Integer stock;

    public Integer getItemId() {
        return itemId;
    }

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

    public Double getDiscount() {
        return discount;
    }

    public Integer getStock() {
        return stock;
    }
}
