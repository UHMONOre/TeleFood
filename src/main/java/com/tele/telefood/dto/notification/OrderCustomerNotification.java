package com.tele.telefood.dto.notification;

import com.tele.telefood.entity.Order;

public record OrderCustomerNotification(
        Integer orderId,
        String message,
        Order.DeliveryMethod newDeliveryMethod
) {}
