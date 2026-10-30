package com.tele.telefood.service;

import com.tele.telefood.dto.ReceiveUserInfo;
import com.tele.telefood.entity.Customer;
import com.tele.telefood.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public ReceiveUserInfo getCustomerInfo(Integer id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        ReceiveUserInfo receiveUserInfo = new ReceiveUserInfo(customer);

        return receiveUserInfo;
    }
}
