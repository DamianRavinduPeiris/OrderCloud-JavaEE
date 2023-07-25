package com.damian.javee.util;

import com.damian.javee.dto.Customer_DTO;
import com.damian.javee.dto.Item_Dto;
import com.damian.javee.dto.Order_DTO;
import com.damian.javee.entity.Customer;
import com.damian.javee.entity.Item;
import com.damian.javee.entity.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class Convertor {
    public static Customer convertToCustomer(Customer_DTO customer_dto) {
        return new Customer(customer_dto.getCustomer_id(), customer_dto.getCustomer_name(), customer_dto.getCustomer_address(), customer_dto.getCustomer_email());
    }

    public static Customer_DTO convertToCustomerDTO(Customer search) {
        return new Customer_DTO(search.getCustomer_id(), search.getCustomer_name(), search.getCustomer_address(), search.getCustomer_email());
    }

    public static List<Customer_DTO> convertToCustomerDTOList(List<Customer> all) {
        List<Customer_DTO> customer_dtos = new ArrayList<>();
        for (Customer customer : all) {
            customer_dtos.add(new Customer_DTO(customer.getCustomer_id(), customer.getCustomer_name(), customer.getCustomer_address(), customer.getCustomer_email()));
        }
        return customer_dtos;
    }

    public static Item convertToItem(Item_Dto item_dto) {
        return new Item(item_dto.getItem_id(), item_dto.getItem_name(), item_dto.getItem_price(), item_dto.getItem_qty());
    }

    public static Item_Dto convertToItemDTO(Item search) {
        return new Item_Dto(search.getItem_id(), search.getItem_name(), search.getItem_price(), search.getItem_qty());
    }

    public static List<Item_Dto> convertToItemDTOList(List<Item> all) {
        ArrayList<Item_Dto> item_dtos = new ArrayList<>();
        for (Item item : all) {
            item_dtos.add(new Item_Dto(item.getItem_id(), item.getItem_name(), item.getItem_price(), item.getItem_qty()));
        }
        return item_dtos;
    }

    public static OrderDetails toOrder(Order_DTO orderDto) {
        System.out.println("price when converting "+orderDto.getItem_price());
        return new OrderDetails(orderDto.getOrder_id(), orderDto.getItem_id(), orderDto.getCustomer_name(), orderDto.getItem_name(), orderDto.getItem_qty(), orderDto.getItem_price(), orderDto.getTotal(), orderDto.getCustomer_id());
    }

    public static Order_DTO toOrderDTO(OrderDetails orderDetails) {
        return new Order_DTO(orderDetails.getOrder_id(), orderDetails.getItem_id(), orderDetails.getCustomer_name(), orderDetails.getItem_name(), orderDetails.getItem_qty(), orderDetails.getItem_price(), orderDetails.getTotal(), orderDetails.getCustomer_id());
    }

    //to order_dto list
    public static List<Order_DTO> toOrderDTOList(List<OrderDetails> all) {
        ArrayList<Order_DTO> order_dtos = new ArrayList<>();
        for (OrderDetails orderDetails : all) {
            System.out.println("price when converting "+orderDetails.getItem_price());
            order_dtos.add(new Order_DTO(orderDetails.getOrder_id(), orderDetails.getItem_id(), orderDetails.getCustomer_name(), orderDetails.getItem_name(), orderDetails.getItem_qty(), orderDetails.getItem_price(), orderDetails.getTotal(), orderDetails.getCustomer_id()));
        }
        return order_dtos;
    }


}
