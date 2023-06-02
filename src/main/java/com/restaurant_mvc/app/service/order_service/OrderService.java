package com.restaurant_mvc.app.service.order_service;

import com.restaurant_mvc.app.domain.FileType;
import com.restaurant_mvc.app.domain.Meal;
import com.restaurant_mvc.app.domain.Order;
import com.restaurant_mvc.app.domain.OrderStatus;
import com.restaurant_mvc.app.errors.GenericException;
import com.restaurant_mvc.app.errors.OrderNotFoundException;
import com.restaurant_mvc.app.errors.OutOfStockException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface OrderService {

    void createOrder(Integer id, OrderStatus status, List<Meal> mealList) throws GenericException, OutOfStockException;

    void updateStatus(Integer id, OrderStatus order) throws GenericException;

    Order getOrder(Integer id) throws GenericException, OrderNotFoundException;

    Integer getLastIdOrZero();

    List<Order> getOrdersBetweenTwoDates(Timestamp startDate, Timestamp endDate);

    List<Meal> getMostOrderedMealsBetweenDates(Timestamp startDate, Timestamp endDate);

    void exportFile(List<Order> orders, FileType fileType);

}
