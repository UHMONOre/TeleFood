package com.tele.telefood.dto;

import com.tele.telefood.entity.Order;
import com.tele.telefood.entity.OrderItem;

import java.util.List;

public class ReceiveSingleOrderInfo {
    private Integer orderId;
    private Order.OrderStatus status;
    private Order.DeliveryMethod method;
    private List<OrderItem> items;
    private Double total;
    private Integer customerId;
    private Integer vendorId;
    private Integer courierId;

    public ReceiveSingleOrderInfo(Order order) {
        this.orderId = order.getId();
        this.status = order.getStatus();
        this.method = order.getMethod();
        this.items = order.getItems();
        this.total = order.getTotal();
        this.customerId = order.getCustomer().getId();
        this.vendorId = order.getVendor().getId();
        this.courierId = order.getCourier().getId();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Order.OrderStatus getStatus() {
        return status;
    }

    public Order.DeliveryMethod getMethod() {
        return method;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public Integer getCourierId() {
        return courierId;
    }
}
