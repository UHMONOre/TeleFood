package com.tele.telefood.service;

import com.tele.telefood.dto.*;
import com.tele.telefood.entity.Courier;
import com.tele.telefood.entity.Customer;
import com.tele.telefood.entity.User;
import com.tele.telefood.entity.Vendor;
import com.tele.telefood.repository.CourierRepository;
import com.tele.telefood.repository.CustomerRepository;
import com.tele.telefood.repository.UserRepository;
import com.tele.telefood.repository.VendorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Transactional
    public Integer processLogin(@RequestBody LoginRequest loginRequest) throws Exception {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new Exception("User not found"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new Exception("Wrong password");
        }

        user.setLoggedInFlag(true);
        user.setLoginTime(LocalDateTime.now());

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public Integer processRegister(@RequestBody RegisterRequestCustomer registerRequestCustomer) throws Exception {
        if (userRepository.findByEmail(registerRequestCustomer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        Customer customer = new Customer(registerRequestCustomer);
        customerRepository.save(customer);
        return customer.getId();
    }

    @Transactional
    public Integer processRegister(@RequestBody RegisterRequestVendor registerRequestVendor) throws Exception {
        if (userRepository.findByEmail(registerRequestVendor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }else if (vendorRepository.findByTin(registerRequestVendor.getTin()).isPresent()){
            throw new IllegalArgumentException("Vendor already exists");
        }

        Vendor vendor = new Vendor(registerRequestVendor);
        vendorRepository.save(vendor);
        return vendor.getId();
    }

    @Transactional
    public Integer processRegister(@RequestBody RegisterRequestCourier registerRequestCourier) throws Exception {
        if (userRepository.findByEmail(registerRequestCourier.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }else if (vendorRepository.findByTin(registerRequestCourier.getTin()).isPresent()){
            throw new IllegalArgumentException("Courier already exists");
        }

        Courier courier = new Courier(registerRequestCourier);
        courierRepository.save(courier);
        return courier.getId();
    }

    @Transactional
    public void updateName(Integer id, String firstName, String lastName) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setFirstName(firstName);
        user.setLastName(lastName);

        userRepository.save(user);
    }

    @Transactional
    public void updateEmail(Integer id, String email) {

        User user  = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEmail(email);

        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Integer id, String password) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setPassword(password);

        userRepository.save(user);
    }

    @Transactional
    public void updateCountry(Integer id, UpdateLocationRequest request) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setCountry(request.getCountry());
        user.setCity(request.getCity());
        user.setAddress(request.getAddress());

        userRepository.save(user);
    }

    @Transactional
    public void updateCity(Integer id, UpdateLocationRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setCity(request.getCity());
        user.setAddress(request.getAddress());

        userRepository.save(user);
    }

    @Transactional
    public void updateAddress(Integer id, UpdateLocationRequest request) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setAddress(request.getAddress());

        userRepository.save(user);
    }

    @Transactional
    public void updatePhone(Integer id, String phoneNumber) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        user.setPhoneNumber(phoneNumber);

        userRepository.save(user);
    }
}
