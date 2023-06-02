package com.restaurant_mvc.app.service.order_service;

import com.restaurant_mvc.app.domain.FileType;
import com.restaurant_mvc.app.domain.Meal;
import com.restaurant_mvc.app.domain.Order;
import com.restaurant_mvc.app.domain.OrderStatus;
import com.restaurant_mvc.app.errors.GenericException;
import com.restaurant_mvc.app.errors.OrderNotFoundException;
import com.restaurant_mvc.app.errors.OutOfStockException;
import com.restaurant_mvc.app.repository.MealRepository;
import com.restaurant_mvc.app.repository.OrderRepository;
import com.restaurant_mvc.app.service.export.CSVExporter;
import com.restaurant_mvc.app.service.export.ExcelExporter;
import com.restaurant_mvc.app.service.export.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MealRepository mealRepository;

    @Override
    public void createOrder (Integer id, OrderStatus status, List<Meal> mealList) throws GenericException, OutOfStockException {
        List<Meal> uniqueMeals = mealList.stream()
                .collect(Collectors.groupingBy(
                        Meal::getName,
                        Collectors.collectingAndThen(
                                Collectors.reducing((m1, m2) -> new Meal(
                                        m1.getName(),
                                        m1.getPrice(),
                                        m1.getStock() + m2.getStock()
                                )),
                                Optional::get
                        )
                ))
                .values().stream()
                .toList();

        Double total = uniqueMeals.stream()
                .mapToDouble(meal -> meal.getPrice() * meal.getStock())
                .sum();

        for(Meal meal: uniqueMeals) {
            Optional<Meal> response = mealRepository.findById (meal.getName ());
            if (response.isPresent ()) {
                Meal dbMeal = response.get ();
                if(dbMeal.getStock () < meal.getStock ())
                    throw new OutOfStockException ();
            }
            else
                throw new GenericException ();
        }

        orderRepository.save (new Order (
                id,
                total,
                Timestamp.from (Instant.now ()),
                status,
                uniqueMeals
        ));
    }

    @Override
    public void updateStatus (Integer id, OrderStatus order) {
        orderRepository.updateStock (id, order);
    }

    @Override
    public Order getOrder (Integer id) throws OrderNotFoundException {
        Optional<Order> response = orderRepository.findById (id);
        if (response.isPresent ())
            return response.get ();
        else
            throw new OrderNotFoundException ();
    }

    @Override
    public Integer getLastIdOrZero () {
        return Math.toIntExact (orderRepository.count ());
    }

    @Override
    public List<Order> getOrdersBetweenTwoDates (Timestamp startDate, Timestamp endDate) {
        return orderRepository.findOrdersBetweenTimestamps (startDate, endDate);
    }

    @Override
    public void exportFile(List<Order> orders, FileType fileType) {
        Exporter exporter = null;
        switch (fileType) {
            case CSV:
                exporter = new CSVExporter ();
                break;
            case EXCEL:
                exporter = new ExcelExporter ();
                break;
            default:
                break;
        }
        try {
            exporter.exportFile (orders);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public List<Meal> getMostOrderedMealsBetweenDates(Timestamp startDate, Timestamp endDate) {
        List<Order> orders = orderRepository.findOrdersBetweenTimestamps(startDate, endDate);
        Map<Meal, Integer> mealCountMap = new HashMap<> ();
        for (Order order : orders) {
            List<Meal> meals = order.getMealsCart();
            for (Meal meal : meals) {
                mealCountMap.put(meal, mealCountMap.getOrDefault(meal, 0) + 1);
            }
        }
        List<Meal> mostOrderedMeals = new ArrayList<> (mealCountMap.keySet());
        mostOrderedMeals.sort(Comparator.comparingInt(mealCountMap::get).reversed());
        return mostOrderedMeals;
    }

}
