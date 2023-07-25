package com.damian.javee.dto;

import com.damian.javee.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order_DTO implements SuperDTO, Serializable {


    private String order_id;

    private String item_id;

    private String customer_name;

    private String item_name;

    private int item_qty;

    private double item_price;

    private double total;

    private Customer customer_id;
}
