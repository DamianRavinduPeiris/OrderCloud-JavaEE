package com.damian.javee.dao.impl;

import com.damian.javee.dao.custom.CustomerDAO;
import com.damian.javee.entity.Customer;
import com.damian.javee.util.FactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOIMPL implements CustomerDAO {
    public static String error = "";

    public static String getErrorInfo() {
        return error;
    }

    public static void setErrorInfo(String error) {
        CustomerDAOIMPL.error = error;
    }

    @Override
    public boolean add(Customer customer) {
        System.out.println(customer.toString());
        Session session = FactoryConfiguration.getInstance().getSession();
        System.out.println(session.isOpen());
        session.beginTransaction();
        try {
            session.save(customer);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            setErrorInfo(e.getLocalizedMessage());
            session.getTransaction().rollback();
            return false;

        }


    }

    @Override
    public boolean update(Customer customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            session.update(customer);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            setErrorInfo(e.getLocalizedMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            session.createQuery("delete from Customer where customer_id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            setErrorInfo(e.getLocalizedMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public Optional<Customer> search(String name) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            Customer customer = session.createQuery("from Customer where customer_name = :name", com.damian.javee.entity.Customer.class).setParameter("name", name).getSingleResult();
            session.close();
            return Optional.of(customer);

        } catch (Exception e) {
            setErrorInfo(e.getLocalizedMessage());
            session.getTransaction().rollback();
            return Optional.empty();


        }


    }

    @Override
    public List<Customer> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();

        try {
            List<Customer> customersList = (ArrayList<Customer>) session.createQuery("from Customer").list();
            return customersList;
        } catch (Exception e) {
            setErrorInfo(e.getLocalizedMessage());
            session.getTransaction().rollback();

        }
        return null;

    }


}
