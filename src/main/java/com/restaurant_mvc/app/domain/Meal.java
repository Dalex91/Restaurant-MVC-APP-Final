package com.restaurant_mvc.app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "meals")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Meal {
    @Id
    @Column(length = 55)
    private String name;
    private Double price;
    private Integer stock;
    @ManyToMany(mappedBy = "mealsCart")
    private List<Order> orders;

    public Meal (String name, Double price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
