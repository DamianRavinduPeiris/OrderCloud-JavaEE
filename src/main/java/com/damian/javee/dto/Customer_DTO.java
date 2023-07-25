package com.damian.javee.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class Customer_DTO implements SuperDTO, Serializable {
    private String customer_id;
    private String customer_name;
    private String customer_address;
    private String customer_email;

}
