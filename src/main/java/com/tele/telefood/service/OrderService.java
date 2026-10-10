package com.tele.telefood.service;

import com.tele.telefood.dto.OrderItemRequest;
import com.tele.telefood.entity.Customer;
import com.tele.telefood.entity.Item;
import com.tele.telefood.entity.Order;
import com.tele.telefood.entity.OrderItem;
import com.tele.telefood.repository.CustomerRepository;
import com.tele.telefood.repository.ItemRepository;
import com.tele.telefood.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

            currentItem.setStock(currentItem.getStock() - req.getQuantity());

            OrderItem orderItem = new OrderItem(order, currentItem, req.getQuantity(), currentItem.getTotal());

            order.getItems().add(orderItem);

            finalTotal += (currentItem.getTotal() * req.getQuantity());
        }

        order.setTotal(finalTotal);
        orderRepository.save(order);
    }
}
