package com.damian.javee.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@RequiredArgsConstructor


public class OrderDetails implements SuperEntity, Serializable {
    @Id
    @NonNull
    private String order_id;
    @NonNull
    private String item_id;
    @NonNull
    private String customer_name;
    @NonNull
    private String item_name;
    @NonNull
    private int item_qty;
    @NonNull
    private double item_price;
    @NonNull
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer_id;

}
