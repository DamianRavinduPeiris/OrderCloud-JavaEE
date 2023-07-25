package com.damian.javee.service.impl;

import com.damian.javee.dao.impl.OrderDAOIMPL;
import com.damian.javee.dao.util.DAOFactory;
import com.damian.javee.dao.util.DAOTypes;
import com.damian.javee.dto.Order_DTO;
import com.damian.javee.service.custom.OrderService;
import com.damian.javee.util.Convertor;

import java.util.ArrayList;
import java.util.Optional;

public class OrderServiceIMPL implements OrderService {
    @Override
    public boolean add(Order_DTO orderDto) {
        OrderDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ORDER_DAO);
        return dao.add(Convertor.toOrder(orderDto));
    }

    @Override
    public boolean update(Order_DTO orderDto) {
        OrderDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ORDER_DAO);
        return dao.update(Convertor.toOrder(orderDto));
    }

    @Override
    public boolean delete(String s) {
        OrderDAOIMPL dao = (OrderDAOIMPL) DAOFactory.getDAO(DAOTypes.ORDER_DAO);
        return  dao.delete(s);
    }

    @Override
    public Optional<Order_DTO> search(String s) {


        return Optional.empty();
    }

    @Override
    public ArrayList<Order_DTO> getAll() {
        OrderDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ORDER_DAO);
        return dao.getAll().isEmpty() ? null : Convertor.toOrderDTOList(dao.getAll());
    }


}
