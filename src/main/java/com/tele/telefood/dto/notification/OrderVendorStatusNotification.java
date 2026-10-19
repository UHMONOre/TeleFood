package com.tele.telefood.dto.notification;

import com.tele.telefood.entity.Order;

public record OrderVendorStatusNotification(
        Integer orderId,
        String message,
        Order.OrderStatus status
) {
}
