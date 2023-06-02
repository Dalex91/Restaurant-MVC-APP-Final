package com.restaurant_mvc.app.errors;

import com.restaurant_mvc.app.utils.BaseError;
import com.restaurant_mvc.app.utils.Constants;

public final class LoginException extends BaseError {

    public LoginException() {
        super(Constants.LOGIN_FAILED);
    }
}
