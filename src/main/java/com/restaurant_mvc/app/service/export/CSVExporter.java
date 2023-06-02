package com.restaurant_mvc.app.service.export;

import com.restaurant_mvc.app.domain.Order;
import com.restaurant_mvc.app.utils.Constants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter implements Exporter{

    @Override
    public void exportFile (List<Order> orders) throws IOException {
        FileWriter fileWriter = new FileWriter (Constants.DEFAULT_PATH);
        String[] headers = {"ID", "Total", "Order Date", "Status"};
        try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(headers))) {
            for (Order order : orders) {
                String id = String.valueOf(order.getId());
                String total = String.valueOf(order.getTotal());
                String orderDate = order.getOrderDateTime().toString();
                String status = String.valueOf (order.getStatus());
                csvPrinter.printRecord(id, total, orderDate, status);
            }
            csvPrinter.flush();
        }
    }
}
