package com.restaurant_mvc.app.service.user_service;

import com.restaurant_mvc.app.domain.User;
import com.restaurant_mvc.app.errors.CreateAccountException;
import com.restaurant_mvc.app.errors.LoginException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User login(String username, String password) throws LoginException;

    void createAccount(User user) throws CreateAccountException;

    List<User> getAllUsers();
}
