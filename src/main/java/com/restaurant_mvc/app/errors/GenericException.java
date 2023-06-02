package com.restaurant_mvc.app.errors;

import com.restaurant_mvc.app.utils.BaseError;
import com.restaurant_mvc.app.utils.Constants;

public class GenericException extends BaseError {

    public GenericException() {
        super(Constants.GENERIC_ERROR);
    }
}
