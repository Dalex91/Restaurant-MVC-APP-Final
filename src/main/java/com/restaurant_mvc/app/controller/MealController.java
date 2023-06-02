package com.restaurant_mvc.app.controller;

import com.restaurant_mvc.app.domain.Meal;
import com.restaurant_mvc.app.errors.GenericException;
import com.restaurant_mvc.app.service.meal_service.MealService;
import com.restaurant_mvc.app.service.order_service.OrderService;
import com.restaurant_mvc.app.utils.EndPoints;
import com.restaurant_mvc.app.utils.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Controller
public class MealController {

    @Autowired
    private MealService mealService;
    @Autowired
    private OrderService orderService;

    @GetMapping(EndPoints.ADMIN_ENDPOINT + EndPoints.MANAGE_PRODUCTS_ENDPOINT)
    public String getManageProductPage(Model model) {
        model.addAttribute (ModelAttributes.MEAL, new Meal ());
        return EndPoints.MANAGE_PRODUCTS_PAGE;
    }

    @PostMapping(EndPoints.MANAGE_PRODUCTS_ENDPOINT)
    public String saveMeal(@ModelAttribute(ModelAttributes.MEAL) Meal meal) {
        try {
            mealService.createMeal (meal);
            return EndPoints.REDIRECT_ADMIN_PAGE;
        } catch (GenericException e) {
            e.printStackTrace ();
            return EndPoints.REDIRECT_MANAGE_PRODUCTS;
        }
    }

    @GetMapping(EndPoints.ALL_MEALS_ENDPOINT)
    public String getAllUsers(Model model) {
        model.addAttribute (ModelAttributes.MEALS, mealService.getAllMeals());
        return EndPoints.ALL_MEALS_PAGE;
    }

    @PostMapping("/update-meal/{id}")
    public String updateProduct (@ModelAttribute(ModelAttributes.MEAL) Meal meal, @PathVariable String id) {
        try {
            mealService.updateMeal (meal);
            return EndPoints.REDIRECT_ADMIN_PAGE;
        } catch (GenericException e) {
            e.printStackTrace ();
        }
        return EndPoints.MANAGE_PRODUCTS_PAGE;
    }

    @PostMapping(EndPoints.MOST_ORDERED_MEALS_ENDPOINT)
    public String getTheMostOrderedMeals(
            @RequestParam(ModelAttributes.START_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(ModelAttributes.END_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model
    ) {

        model.addAttribute (
                ModelAttributes.MEALS,
                orderService.getMostOrderedMealsBetweenDates (
                        Timestamp.valueOf (startDate.atStartOfDay ()),
                        Timestamp.valueOf (endDate.atStartOfDay ().plusDays (1).minusSeconds (1))
                )
        );
        return EndPoints.MOST_ORDERED_MEALS_PAGE;
    }
}
