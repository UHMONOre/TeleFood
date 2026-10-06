package com.tele.telefood.service;

import com.tele.telefood.entity.Vendor;
import com.tele.telefood.repository.VendorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Transactional
    public void UpdateTin(Integer id, long tin) {

        Vendor vendor  = vendorRepository.findById(id).orElseThrow(() -> new RuntimeException("Vendor not found"));

        if (vendorRepository.findByTin(tin).isEmpty()) {
            vendor.setTin(tin);
            vendorRepository.save(vendor);
        }else if (vendor.getTin() == tin) {
            throw new IllegalArgumentException("Tin is already in use");
        }else {
            throw new IllegalArgumentException("A vendor with the same tin already exists");
        }
    }

    @Transactional
    public void UpdateName(Integer id, String name) {

        Vendor vendor  = vendorRepository.findById(id).orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setName(name);

        vendorRepository.save(vendor);
    }
}
