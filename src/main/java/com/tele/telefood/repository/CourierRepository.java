package com.tele.telefood.repository;

import com.tele.telefood.entity.Courier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends CrudRepository<Courier,Integer> {
    Optional<Courier> findById(Integer id);

    Optional<Courier> findByTin(Long tin);

    @Query("SELECT c FROM Courier c WHERE c.city = :city AND c.availability = true AND c NOT IN :rejectedCouriers")
    List<Courier> findAvailableCouriersExcludingRejected(
            @Param("city") String city,
            @Param("rejectedCouriers") List<Courier> rejectedCouriers
    );

}
