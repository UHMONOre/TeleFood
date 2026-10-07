package com.tele.telefood.repository;

import com.tele.telefood.entity.Courier;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourierRepository extends CrudRepository<Courier,Integer> {
    Optional<Courier> findById(Integer id);

    Optional<Courier> findByTin(Long tin);
}
