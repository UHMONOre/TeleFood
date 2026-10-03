package com.tele.telefood;

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
}
