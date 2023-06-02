package com.restaurant_mvc.app.errors;

import com.restaurant_mvc.app.utils.BaseError;
import com.restaurant_mvc.app.utils.Constants;

public class OrderNotFoundException extends BaseError {

    public OrderNotFoundException () {
        super (Constants.ORDER_NOT__FOUND);
    }
}
