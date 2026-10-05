package com.tele.telefood.service;

import com.tele.telefood.dto.LoginRequest;
import com.tele.telefood.dto.RegisterRequestCustomer;
import com.tele.telefood.dto.RegisterRequestVendor;
import com.tele.telefood.entity.Customer;
import com.tele.telefood.entity.User;
import com.tele.telefood.entity.Vendor;
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
}
