package com.damian.javee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Item_Dto implements SuperDTO, Serializable {
    private String item_id;
    private String item_name;
    private double item_price;
    private int item_qty;

}
