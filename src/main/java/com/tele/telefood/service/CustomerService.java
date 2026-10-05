package com.tele.telefood.service;

import com.tele.telefood.dto.UpdateLocationRequest;
import com.tele.telefood.entity.Customer;
import com.tele.telefood.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void updateCountry(Integer id, UpdateLocationRequest request) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setCountry(request.getCountry());
        customer.setCity(request.getCity());
        customer.setAddress(request.getAddress());

        customerRepository.save(customer);
    }

    @Transactional
    public void updateCity(Integer id, UpdateLocationRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setCity(request.getCity());
        customer.setAddress(request.getAddress());

        customerRepository.save(customer);
    }

    @Transactional
    public void updateAddress(Integer id, UpdateLocationRequest request) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setAddress(request.getAddress());

        customerRepository.save(customer);
    }

    @Transactional
    public void updatePhone(Integer id, String phoneNumber) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setPhoneNumber(phoneNumber);

        customerRepository.save(customer);
    }
}
