package com.tele.telefood.service;

import com.tele.telefood.dto.OrderItemRequest;
import com.tele.telefood.dto.notification.OrderCourierNotification;
import com.tele.telefood.dto.notification.OrderCustomerNotification;
import com.tele.telefood.dto.notification.OrderVendorNotification;
import com.tele.telefood.entity.*;
import com.tele.telefood.repository.CourierRepository;
import com.tele.telefood.repository.CustomerRepository;
import com.tele.telefood.repository.ItemRepository;
import com.tele.telefood.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    @Lazy
    private OrderService self;

    @Autowired
    private org.springframework.scheduling.TaskScheduler taskScheduler;

    @Transactional
    public void startCourierAssignment(Integer orderId) {
        assignNextAvailableCourier(orderId);
    }

    @Transactional
    public void createOrder(Integer customerId, List<OrderItemRequest> requestedItems) {
        if (requestedItems == null || requestedItems.isEmpty()) {
            throw new IllegalArgumentException("Order must not be empty");
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(()  -> new RuntimeException("Customer not found"));

        Item masterItem = itemRepository.findById(requestedItems.get(0).getItemId()).orElseThrow(() -> new RuntimeException("Item not found"));

        Order order = new Order(customer, masterItem.getVendor()); //item.getVendor is the master vendor

        double finalTotal = 0.0;

        for (OrderItemRequest req : requestedItems) {
            Item currentItem = itemRepository.findById(req.getItemId()).orElseThrow(() -> new RuntimeException("Item not found"));

            if (!currentItem.getVendor().getId().equals(masterItem.getVendor().getId())) {
                throw new IllegalArgumentException("Cannot mix items from different vendors");
            }

            if (currentItem.getStock() < req.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for item: " + currentItem.getName() + ". Only " + currentItem.getStock() + " left.");
            }

            currentItem.setStock(currentItem.getStock() - req.getQuantity());

            OrderItem orderItem = new OrderItem(order, currentItem, req.getQuantity(), currentItem.getTotal());

            order.getItems().add(orderItem);

            finalTotal += (currentItem.getTotal() * req.getQuantity());
        }

        order.setTotal(finalTotal);
        orderRepository.save(order);

        OrderVendorNotification notification = new OrderVendorNotification(order.getId(), order.getTotal());

        String vendorChannel = "/topic/vendor/" + order.getVendor().getId();

        messagingTemplate.convertAndSend(vendorChannel, notification);

        startCourierAssignment(order.getId());
    }

    @Transactional
    public void assignNextAvailableCourier(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        List<Courier> nextCouriers = courierRepository.findAvailableCouriersExcludingRejected(
                order.getVendor().getCity(),
                order.getRejectedCouriers()
        );

        if (nextCouriers.isEmpty()) {
            order.setMethod(Order.DeliveryMethod.Takeout);
            orderRepository.save(order);

            OrderCustomerNotification customerNotification = new OrderCustomerNotification(
                    order.getId(),
                    "No couriers available. Delivery changed to Takeout.",
                    Order.DeliveryMethod.Takeout
            );

            messagingTemplate.convertAndSend("/topic/customer/" + order.getCustomer().getId(), customerNotification);
        } else {
            Courier nextCourier = nextCouriers.get(0);
            order.setCourier(nextCourier);
            orderRepository.save(order);

            OrderCourierNotification courierNotification = new OrderCourierNotification(
                    order.getId(),
                    order.getTotal(),
                    order.getVendor().getName()
            );

            messagingTemplate.convertAndSend("/topic/courier/" + nextCourier.getId(), courierNotification);

            taskScheduler.schedule(
                    () -> self.autoRejectIfUnanswered(orderId, nextCourier.getId()),
                    java.time.Instant.now().plusSeconds(60)
            );
        }
    }

    @Transactional
    public void processCourierRejection(Integer orderId, Integer courierId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        Courier rejectedCourier = courierRepository.findById(courierId).orElseThrow(() -> new RuntimeException("Courier not found"));

        if (order.getCourier() != null && order.getCourier().getId().equals(courierId)) {
            order.getRejectedCouriers().add(rejectedCourier);
            order.setCourier(null);
            orderRepository.save(order);

            assignNextAvailableCourier(orderId);
        }
    }

    @Transactional
    public void autoRejectIfUnanswered(Integer orderId, Integer courierId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return;
        }

        if (order.getCourier() != null
            && order.getCourier().getId().equals(courierId)
            && order.getMethod() == Order.DeliveryMethod.Pending) {

            processCourierRejection(orderId, courierId);
        }
    }
}
