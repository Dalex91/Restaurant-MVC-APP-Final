package com.restaurant_mvc.app.errors;


import com.restaurant_mvc.app.utils.BaseError;
import com.restaurant_mvc.app.utils.Constants;

public final class UserNotFoundException extends BaseError {

    public UserNotFoundException() {
        super(Constants.USER_NOT_FOUND);
    }
}
