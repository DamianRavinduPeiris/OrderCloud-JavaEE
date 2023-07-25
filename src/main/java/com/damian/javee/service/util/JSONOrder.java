package com.damian.javee.service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JSONOrder {

    private String order_id;

    private String item_id;
    private String customer_name;

    private String item_name;

    private int item_qty;

    private double item_price;

    private double total;
}
