package com.tele.telefood.dto.notification;

public record OrderCourierNotification(
        Integer orderId,
        Double total,
        String vendorName
) {}
