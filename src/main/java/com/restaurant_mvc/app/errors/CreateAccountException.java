package com.restaurant_mvc.app.errors;

import com.restaurant_mvc.app.utils.BaseError;
import com.restaurant_mvc.app.utils.Constants;
public final class CreateAccountException extends BaseError {

    public CreateAccountException() {
        super(Constants.CREATE_ACCOUNT_FAILED);
    }
}
