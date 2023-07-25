package com.damian.javee.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
public class Customer implements SuperEntity, Serializable {
    @Id
    @NonNull
    private String customer_id;
    @NonNull
    private String customer_name;
    @NonNull
    private String customer_address;
    @NonNull
    private String customer_email;
    @OneToMany(targetEntity = OrderDetails.class, mappedBy = "customer_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> ordersList = new ArrayList<>();

}
