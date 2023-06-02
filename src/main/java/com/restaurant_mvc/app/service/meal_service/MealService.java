package com.restaurant_mvc.app.service.meal_service;

import com.restaurant_mvc.app.domain.Meal;
import com.restaurant_mvc.app.errors.GenericException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MealService {

    void createMeal(Meal meal) throws GenericException;

    Meal getMeal(String name) throws GenericException;

    List<Meal> getAllMeals();

    List<Meal> getMealsByIds(List<String> ids);

    void updateMeal(Meal meal) throws GenericException;

    void updateStock(String name, Integer stock) throws GenericException;

    void deleteMeal(String name) throws GenericException;
}
