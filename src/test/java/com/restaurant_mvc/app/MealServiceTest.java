package com.restaurant_mvc.app;

import com.restaurant_mvc.app.domain.Meal;
import com.restaurant_mvc.app.errors.GenericException;
import com.restaurant_mvc.app.repository.MealRepository;
import com.restaurant_mvc.app.service.meal_service.MealService;
import com.restaurant_mvc.app.service.meal_service.MealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService = new MealServiceImpl();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMeal() throws GenericException {
        Meal meal = new Meal("Burger", 10.99, 100);
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        assertDoesNotThrow(() -> mealService.createMeal(meal));
        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void testGetMeal() throws GenericException {
        Meal meal = new Meal("Burger", 10.99, 100);
        when(mealRepository.findById("Burger")).thenReturn(Optional.of(meal));

        Meal retrievedMeal = mealService.getMeal("Burger");
        assertNotNull(retrievedMeal);
        assertEquals(meal, retrievedMeal);
        verify(mealRepository, times(1)).findById("Burger");
    }

    @Test
    void testGetMealNotFound() {
        when(mealRepository.findById("Burger")).thenReturn(Optional.empty());
        assertThrows(GenericException.class, () -> mealService.getMeal("Burger"));
        verify(mealRepository, times(1)).findById("Burger");
    }

    @Test
    void testGetAllMeals() {
        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal("Burger", 10.99, 100));
        meals.add(new Meal("Pizza", 12.99, 50));
        when(mealRepository.findAll()).thenReturn(meals);

        List<Meal> retrievedMeals = mealService.getAllMeals();
        assertNotNull(retrievedMeals);
        assertEquals(meals.size(), retrievedMeals.size());
        assertEquals(meals, retrievedMeals);
        verify(mealRepository, times(1)).findAll();
    }
}
