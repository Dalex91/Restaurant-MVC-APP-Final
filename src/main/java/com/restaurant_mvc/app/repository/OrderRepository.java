package com.restaurant_mvc.app.repository;

import com.restaurant_mvc.app.domain.Order;
import com.restaurant_mvc.app.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Query("UPDATE Order SET status = :status WHERE id = :id")
    int updateStock(@Param("id") Integer id, @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.orderDateTime BETWEEN :startTimestamp AND :endTimestamp")
    List<Order> findOrdersBetweenTimestamps(@Param("startTimestamp") Timestamp startTimestamp, @Param("endTimestamp") Timestamp endTimestamp);
}
