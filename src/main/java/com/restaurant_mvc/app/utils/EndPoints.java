package com.restaurant_mvc.app.utils;

public final class EndPoints {

    public static final String ADMIN_ENDPOINT = "/admin-page";
    public static final String ADMIN_PAGE = "admin-page";
    public static final String REDIRECT_ADMIN_PAGE = "redirect:/admin-page";

    public static final String MANAGE_USERS_ENDPOINT = "/manage-users";
    public static final String MANAGE_USERS_PAGE = "manage-users";

    public static final String ALL_USERS_ENDPOINT = "/all-users";
    public static final String ALL_USERS_PAGE = "all-users";

    public static final String MANAGE_ORDERS_ENDPOINT = "/manage-orders";
    public static final String MANAGE_ORDERS_PAGE = "manage-orders";

    public static final String ORDER_DATE_ENDPOINT = "/order-date-result";
    public static final String ORDER_DATE_PAGE = "order-date-result";

    public static final String CREATE_ORDER_ENDPOINT = "/create-order";
    public static final String CREATE_ORDER_PAGE = "create-order";
    public static final String REDIRECT_MANAGE_ORDERS = "redirect:/admin-page/manage-orders";

    public static final String MANAGE_PRODUCTS_ENDPOINT = "/manage-products";
    public static final String MANAGE_PRODUCTS_PAGE = "manage-products";
    public static final String REDIRECT_MANAGE_PRODUCTS = "redirect:/manage-products";

    public static final String ALL_MEALS_ENDPOINT = "/all-meals";
    public static final String ALL_MEALS_PAGE = "all-meals";

    public static final String UPDATE_MEAL_ENDPOINT = "/update-meal/{id}";

    public static final String UPDATE_STATUS_PAGE = "update_status";
    public static final String UPDATE_STATUS_ENDPOINT= "update_status";

    public static final String EMPLOYEE_PAGE = "employee-page";
    public static final String EMPLOYEE_PAGE_ENDPOINT = "/employee-page";
    public static final String REDIRECT_EMPLOYEE_PAGE = "redirect:/employee-page";

    public static final String LOGIN_PAGE = "login";
    public static final String LOGIN_ENDPOINT = "/login";

    public static final String MOST_ORDERED_MEALS_PAGE = " most-ordered-meals";
    public static final String MOST_ORDERED_MEALS_ENDPOINT = "/ most-ordered-meals";

    public static final String EMPLOYEE_REQUEST = "/employee-page/**";
    public static final String ADMIN_REQUEST = "/admin-page/";
    public static final String GENERIC = "/";
}
