package com.tele.telefood.dto;

import com.tele.telefood.entity.Order;

import java.util.List;

public class ReceiveOrderInfo {
    public List<Order> activeOrders;
    public List<Order> completedOrders;
    public List<Order> cancelledOrders;

    public ReceiveOrderInfo(List<Order> activeOrders, List<Order> completedOrders, List<Order> cancelledOrders) {
        this.activeOrders = activeOrders;
        this.completedOrders = completedOrders;
        this.cancelledOrders = cancelledOrders;
    }

    public List<Order> getActiveOrders() {
        return activeOrders;
    }

    public List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public List<Order> getCancelledOrders() {
        return cancelledOrders;
    }
}
