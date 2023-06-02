package com.restaurant_mvc.app.service.export;

import com.restaurant_mvc.app.domain.Order;

import java.io.IOException;
import java.util.List;

public interface Exporter {

    void exportFile(List<Order> orders) throws IOException;
}
