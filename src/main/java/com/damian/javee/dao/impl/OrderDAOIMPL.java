package com.damian.javee.dao.impl;

import com.damian.javee.dao.custom.OrderDAO;
import com.damian.javee.entity.OrderDetails;
import com.damian.javee.util.FactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Optional;

public class OrderDAOIMPL implements OrderDAO {
    private static String errorInfo = "";

    public static String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        OrderDAOIMPL.errorInfo = errorInfo;
    }

    @Override
    public boolean add(OrderDetails orderDetails) {

        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            session.save(orderDetails);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
            setErrorInfo(e.getLocalizedMessage());
            return false;
        }

    }

    @Override
    public boolean update(OrderDetails orderDetails) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            System.out.println(orderDetails.getOrder_id());

            System.out.println("UQ : " + orderDetails.getItem_qty());
            session.update(orderDetails.getOrder_id(), orderDetails);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            setErrorInfo(e.getLocalizedMessage());
            session.getTransaction().rollback();

        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();

        try {
            System.out.println(id);
            session.createQuery("DELETE FROM OrderDetails WHERE order_id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            setErrorInfo(e.getLocalizedMessage());
            session.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public Optional<OrderDetails> search(String s) {
        return Optional.empty();
    }

    @Override
    public ArrayList<OrderDetails> getAll() {
        System.out.println("get all orders");
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            ArrayList<OrderDetails> orderDetails = (ArrayList<OrderDetails>) session.createQuery("FROM OrderDetails").list();
            return orderDetails;
        } catch (Exception e) {
            session.getTransaction().rollback();
            setErrorInfo(e.getLocalizedMessage());
        }
        return null;

    }


}
