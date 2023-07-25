package com.damian.javee.service.util;

import com.damian.javee.service.impl.CustomerServiceIMPL;
import com.damian.javee.service.impl.ItemServiceIMPL;
import com.damian.javee.service.impl.OrderServiceIMPL;

public class ServiceFactory {
    public static <T> T getService(ServiceTypes serviceType) {
        switch (serviceType) {
            case CUSTOMER_SERVICE:
                return (T) new CustomerServiceIMPL();
            case ITEM_SERVICE:
                return (T) new ItemServiceIMPL();
            case ORDER_SERVICE:
                return (T) new OrderServiceIMPL();

            default:
                throw new RuntimeException("Invalid Service Type.");
        }


    }
}
