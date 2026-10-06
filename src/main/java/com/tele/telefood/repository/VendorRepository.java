package com.tele.telefood.repository;

import com.tele.telefood.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    Optional<Vendor> findByTin(Long tin);

    Optional<Vendor> findById(Integer id);
}
