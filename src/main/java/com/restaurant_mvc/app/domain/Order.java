package com.restaurant_mvc.app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {
    @Id
    private Integer id;
    private Double total;
    private Timestamp orderDateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToMany
    private List<Meal> mealsCart;

    public Order (Integer id, Double total, Timestamp orderDateTime, OrderStatus status) {
        this.id = id;
        this.total = total;
        this.orderDateTime = orderDateTime;
        this.status = status;
    }
}
