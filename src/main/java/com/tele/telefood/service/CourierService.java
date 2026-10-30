package com.tele.telefood.service;

import com.tele.telefood.dto.ReceiveUserInfo;
import com.tele.telefood.entity.Courier;
import com.tele.telefood.entity.Order;
import com.tele.telefood.repository.CourierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierService {
    @Autowired
    private CourierRepository courierRepository;

    @Transactional
    public ReceiveUserInfo getCourierInfo(Integer id){
        Courier courier = courierRepository.findById(id).orElseThrow(() -> new RuntimeException("Courier not found"));

        ReceiveUserInfo receiveUserInfo = new ReceiveUserInfo(courier);

        return receiveUserInfo;
    }

    @Transactional
    public void updateTin(Integer id, Long tin){

        Courier courier = courierRepository.findById(id).orElseThrow(() -> new  RuntimeException("Courier not found"));

        if (courierRepository.findByTin(tin).isEmpty()){
            courier.setTin(tin);
            courierRepository.save(courier);
        }else if (courier.getTin().equals(tin)){
            throw new IllegalArgumentException("Tin is already in use");
        }else {
            throw new IllegalArgumentException("A vendor with the same tin already exists");
        }
    }

    @Transactional
    public void updateAvailability(Integer id, Boolean availability){

        Courier courier = courierRepository.findById(id).orElseThrow(() -> new RuntimeException("Courier not found"));

        List<Order> activeOrders = courierRepository.findActiveOrders(courier.getId());

        if (activeOrders.isEmpty()) {
            courier.setAvailability(availability);

            courierRepository.save(courier);
        } else {
            throw new RuntimeException("Courier with an active order cannot change availability");
        }
    }
}
