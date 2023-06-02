package com.restaurant_mvc.app.errors;

import com.restaurant_mvc.app.utils.BaseError;
import com.restaurant_mvc.app.utils.Constants;

public class OutOfStockException extends BaseError {

    public OutOfStockException () {
        super (Constants.OUT_OF_STOCK);
    }
}
