package com.damian.javee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class Item implements SuperEntity, Serializable {
    @Id
    private String item_id;
    private String item_name;
    private double item_price;
    private int item_qty;

}
