package com.restaurant_mvc.app.controller;

import com.restaurant_mvc.app.domain.FileType;
import com.restaurant_mvc.app.domain.Order;
import com.restaurant_mvc.app.domain.OrderStatus;
import com.restaurant_mvc.app.domain.UserType;
import com.restaurant_mvc.app.errors.GenericException;
import com.restaurant_mvc.app.errors.OutOfStockException;
import com.restaurant_mvc.app.service.meal_service.MealService;
import com.restaurant_mvc.app.service.order_service.OrderService;
import com.restaurant_mvc.app.utils.Constants;
import com.restaurant_mvc.app.utils.EndPoints;
import com.restaurant_mvc.app.utils.ModelAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MealService mealService;

    @GetMapping(EndPoints.ADMIN_ENDPOINT + EndPoints.MANAGE_ORDERS_ENDPOINT)
    public String getManageOrdersPage(Model model) {
        model.addAttribute (ModelAttributes.START_DATE, LocalDate.now ());
        model.addAttribute (ModelAttributes.END_DATE, LocalDate.now ());
        return EndPoints.MANAGE_ORDERS_PAGE;
    }

    @PostMapping(EndPoints.ORDER_DATE_ENDPOINT)
    public String getOrdersBetween(
            @RequestParam(ModelAttributes.START_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(ModelAttributes.END_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request,
            Model model
    ) {
        List<Order> orders = orderService.getOrdersBetweenTwoDates (
                Timestamp.valueOf (startDate.atStartOfDay ()),
                Timestamp.valueOf (endDate.atStartOfDay ().plusDays (1).minusSeconds (1))
        );
        FileType fileType = FileType.valueOf (request.getParameter(ModelAttributes.FILE_TYPE));
        model.addAttribute (ModelAttributes.ORDERS, orders);
        orderService.exportFile (orders, fileType);
        return EndPoints.ORDER_DATE_PAGE;
    }

    @GetMapping(EndPoints.EMPLOYEE_PAGE_ENDPOINT + EndPoints.CREATE_ORDER_ENDPOINT)
    public String getManageUserPage(Model model) {
        model.addAttribute (ModelAttributes.ORDER_ID, 0);
        model.addAttribute (ModelAttributes.PRODUCTS, Constants.EMPTY_STRING);
        return EndPoints.CREATE_ORDER_PAGE;
    }

    @PostMapping(EndPoints.CREATE_ORDER_ENDPOINT)
    public String createOrder(
            @RequestParam(ModelAttributes.ORDER_ID) Integer orderId,
            @RequestParam(ModelAttributes.PRODUCTS) String products
    ) {
        List<String> productsIds = Arrays.asList(products.split(","));
        try {
            orderService.createOrder (
                    orderId,
                    OrderStatus.NEW,
                    mealService.getMealsByIds (productsIds)
            );
            return EndPoints.REDIRECT_EMPLOYEE_PAGE;
        } catch (GenericException | OutOfStockException e) {
            e.printStackTrace ();
        }
        return EndPoints.CREATE_ORDER_PAGE;
    }


    @GetMapping(EndPoints.UPDATE_STATUS_ENDPOINT)
    public String getUpdateStatusPage(Model model) {
        model.addAttribute (ModelAttributes.ORDER, new Order ());
        return EndPoints.UPDATE_STATUS_PAGE;
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus (@PathVariable Integer id, @ModelAttribute(ModelAttributes.ORDER) Order order) {
        try {
            orderService.updateStatus (id, order.getStatus ());
            return EndPoints.REDIRECT_EMPLOYEE_PAGE;
        } catch (GenericException e) {
            e.printStackTrace ();
        }
        return EndPoints.UPDATE_STATUS_PAGE;
    }
}
