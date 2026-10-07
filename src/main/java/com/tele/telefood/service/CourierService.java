package com.tele.telefood.service;

import com.tele.telefood.entity.Courier;
import com.tele.telefood.repository.CourierRepository;
import com.tele.telefood.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourierService {
    @Autowired
    private CourierRepository courierRepository;

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
}
