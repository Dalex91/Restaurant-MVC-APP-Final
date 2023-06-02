package com.restaurant_mvc.app.controller;

import com.restaurant_mvc.app.domain.User;
import com.restaurant_mvc.app.domain.UserType;
import com.restaurant_mvc.app.errors.CreateAccountException;
import com.restaurant_mvc.app.service.user_service.UserService;
import com.restaurant_mvc.app.utils.EndPoints;
import com.restaurant_mvc.app.utils.ModelAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = EndPoints.ADMIN_ENDPOINT, method = RequestMethod.GET)
    public String getAdminPage() {
        return EndPoints.ADMIN_PAGE;
    }

    @RequestMapping(value = EndPoints.EMPLOYEE_PAGE_ENDPOINT, method = RequestMethod.GET)
    public String getEmployeePage() {
        return EndPoints.EMPLOYEE_PAGE;
    }

    @GetMapping(EndPoints.ADMIN_ENDPOINT + EndPoints.MANAGE_USERS_ENDPOINT)
    public String getManageUserPage(Model model) {
        model.addAttribute (ModelAttributes.USER, new User ());
        return EndPoints.MANAGE_USERS_PAGE;
    }

    @PostMapping(EndPoints.MANAGE_USERS_ENDPOINT)
    public String saveUser(@ModelAttribute(ModelAttributes.USER) User user, HttpServletRequest request) {
        user.setUserType (UserType.valueOf (request.getParameter(ModelAttributes.USER_TYPE)));
        try {
            userService.createAccount (user);
            return EndPoints.REDIRECT_ADMIN_PAGE;
        } catch (CreateAccountException e) {
            e.printStackTrace ();
            return EndPoints.MANAGE_USERS_PAGE;
        }
    }

    @GetMapping(EndPoints.ALL_USERS_ENDPOINT)
    public String getAllUsers(Model model) {
        model.addAttribute (ModelAttributes.USERS, userService.getAllUsers ());
        return EndPoints.ALL_USERS_PAGE;
    }

    @GetMapping(EndPoints.LOGIN_ENDPOINT)
    public String getLoginPage() {
        return EndPoints.LOGIN_PAGE;
    }
}
