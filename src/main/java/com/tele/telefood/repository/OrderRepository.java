package com.tele.telefood.repository;

import com.tele.telefood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o " +
            "WHERE o.vendor.id = :vendorId " +
            "AND o.status = com.tele.telefood.entity.Order$OrderStatus.Completed")
    List<Order> findCompletedOrders(@Param("vendorId") Integer vendorId);

    @Query("SELECT o FROM Order o " +
            "WHERE o.vendor.id = :vendorId " +
            "AND o.status = com.tele.telefood.entity.Order$OrderStatus.Failed")
    List<Order> findFailedOrders(@Param("vendorId") Integer vendorId);

    @Query("SELECT o FROM Order o " +
            "WHERE o.vendor.id = :vendorId " +
            "AND o.status NOT IN (" +
            " com.tele.telefood.entity.Order$OrderStatus.Completed, " +
            " com.tele.telefood.entity.Order$OrderStatus.Failed )")
    List<Order> findActiveOrders(@Param("vendorId") Integer vendorId);
}
