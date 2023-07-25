package com.damian.javee.service.impl;

import com.damian.javee.dao.impl.CustomerDAOIMPL;
import com.damian.javee.dao.util.DAOFactory;
import com.damian.javee.dao.util.DAOTypes;
import com.damian.javee.dto.Customer_DTO;
import com.damian.javee.entity.Customer;
import com.damian.javee.service.custom.CustomerService;
import com.damian.javee.util.Convertor;

import java.util.ArrayList;
import java.util.Optional;

public class CustomerServiceIMPL implements CustomerService {
    @Override
    public boolean add(Customer_DTO customerDto) {
        System.out.println("CustomerServiceIMPL.add() triggered");
        CustomerDAOIMPL cd = DAOFactory.getDAO(DAOTypes.CUSTOMER_DAO);
        return cd.add(Convertor.convertToCustomer(customerDto));
    }

    @Override
    public boolean update(Customer_DTO customerDto) {
        CustomerDAOIMPL dao = DAOFactory.getDAO(DAOTypes.CUSTOMER_DAO);
        return dao.update(Convertor.convertToCustomer(customerDto));
    }

    @Override
    public boolean delete(String s) {
        CustomerDAOIMPL dao = DAOFactory.getDAO(DAOTypes.CUSTOMER_DAO);
        return dao.delete(s);
    }

    @Override
    public Optional<Customer_DTO> search(String s) {
        CustomerDAOIMPL dao = DAOFactory.getDAO(DAOTypes.CUSTOMER_DAO);
        Optional<Customer> customer = dao.search(s);
        if (customer.isPresent()) {
            return Optional.of(Convertor.convertToCustomerDTO(customer.get()));

        }
        return Optional.empty();

    }

    @Override
    public ArrayList<Customer_DTO> getAll() {
        System.out.println("CustomerServiceIMPL.getAll() triggered");
        CustomerDAOIMPL dao = (CustomerDAOIMPL) DAOFactory.getDAO(DAOTypes.CUSTOMER_DAO);
        return dao.getAll() == null? null: Convertor.convertToCustomerDTOList(dao.getAll());
    }
}
