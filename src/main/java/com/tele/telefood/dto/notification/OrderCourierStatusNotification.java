package com.tele.telefood.dto.notification;

public record OrderCourierStatusNotification(
        Integer orderId,
        String message,
        boolean success
) { }
