package com.restaurant_mvc.app.service.user_service;

import com.restaurant_mvc.app.domain.User;
import com.restaurant_mvc.app.errors.CreateAccountException;
import com.restaurant_mvc.app.errors.LoginException;
import com.restaurant_mvc.app.repository.UserRepository;
import com.restaurant_mvc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login (String name, String password) throws LoginException {
        Optional<User> result = userRepository.findById (name);
        if (result.isPresent ()) {
            User currentUser = result.get ();
            if (! currentUser.getPassword ().equals (Utils.encrypt (password)))
                throw new LoginException ();
            return currentUser;
        }
        else
            throw new LoginException ();
    }

    private void encryptPassword(User user) {
        user.setPassword (Utils.encrypt (user.getPassword ()));
    }

    @Override
    public void createAccount (User user) throws CreateAccountException {
        encryptPassword (user);
        User savedUser = userRepository.save (user);
        if(savedUser.getUsername () == null)
            throw new CreateAccountException ();
    }

    @Override
    public List<User> getAllUsers () {
        return userRepository.findAll ();
    }
}
